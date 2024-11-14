package com.se.scrumflow.service.impl;

import cn.hutool.core.date.DateUtil;
import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.BurndownRepository;
import com.se.scrumflow.dao.repository.SprintRepository;
import com.se.scrumflow.service.BurndownService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BurndownServiceImpl implements BurndownService {

    @Autowired
    private BurndownRepository burndownRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public BurndownDO createBurndown(ObjectId sprintId) {
        //查出sprintId对应的SprintDO
        Optional<SprintDO> optionalSprint = sprintRepository.findById(sprintId);
        // 检查Burndown是否已经存在
        BurndownDO existingBurndown = burndownRepository.findBySprintId(sprintId);
        if (existingBurndown != null) {
            return existingBurndown; // 如果已存在，则直接返回
        }
        // 如果Burndown不存在，则创建新的Burndown
        SprintDO sprint = optionalSprint.get();
        Date startDate = sprint.getStartDate();
        Date endDate = sprint.getEndDate();
        List<Date> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        while (!calendar.getTime().after(endDate)) {
            dateList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Map<Date, Integer> map = new TreeMap<>();
        Query queryItems = new Query(Criteria
                .where("sprintId").is(sprintId)
                .and("endTime").gte(startDate));
        int total = mongoTemplate.find(queryItems, ItemDO.class).stream().mapToInt(ItemDO::getStoryPoint).sum();
        map.put(dateList.get(0), total);
        for (int i = 1; i < dateList.size(); i++) {
            Date currentDate = dateList.get(i);
            Date startOfDay = DateUtil.beginOfDay(currentDate);
            Date endOfDay = DateUtil.endOfDay(currentDate);
            Query query = new Query(Criteria
                    .where("sprintId").is(sprintId)
                    .and("endTime").lte(endOfDay).gte(startOfDay));
            List<ItemDO> plannedItems = mongoTemplate.find(query, ItemDO.class);
            int shouldDonePoints = plannedItems.stream().mapToInt(ItemDO::getStoryPoint).sum();
            Integer beforeDateStoryPoints = map.get(dateList.get(i - 1));
            map.put(currentDate, beforeDateStoryPoints - shouldDonePoints);
        }
        BurndownDO newBurndownDO = new BurndownDO();
        newBurndownDO.setSprintId(sprintId);
        newBurndownDO.setStartTime(startDate);
        newBurndownDO.setEndTime(endDate);
        newBurndownDO.setPlannedRemainingStoryPoints(map);
        burndownRepository.insert(newBurndownDO);
        return newBurndownDO;
    }


    @Override
    public BurndownDO updateBurndown(ObjectId sprintId) {
        Optional<SprintDO> byId = sprintRepository.findById(sprintId);
        SprintDO sprintDO = byId.get();
        BurndownDO burndownDO = burndownRepository.findBySprintId(sprintId);
        Date startDate = sprintDO.getStartDate();
        Date endDate = sprintDO.getEndDate();

        List<Date> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        while (!calendar.getTime().after(endDate)) {
            dateList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Map<Date, Integer> map = new TreeMap<>();

        Query queryItems = new Query(Criteria
                .where("sprintId").is(sprintId)
                .and("endTime").gte(startDate));
        int total = mongoTemplate.find(queryItems, ItemDO.class).stream().mapToInt(ItemDO::getStoryPoint).sum();
        map.put(dateList.get(0), total);

        for (int i = 1; i < dateList.size(); i++) {
            Date currentDate = dateList.get(i);
            Date startOfDay = DateUtil.beginOfDay(currentDate);
            Date endOfDay = DateUtil.endOfDay(currentDate);
            Query query = new Query(Criteria
                    .where("sprintId").is(sprintId)
                    .and("doneTime").lte(endOfDay).gte(startOfDay));
            List<ItemDO> plannedItems = mongoTemplate.find(query, ItemDO.class);

            int shouldDonePoints = plannedItems.stream().mapToInt(ItemDO::getStoryPoint).sum();
            Integer beforeDateStoryPoints = map.get(dateList.get(i - 1));
            map.put(currentDate, beforeDateStoryPoints - shouldDonePoints);
        }

        burndownDO.setActualRemainingStoryPoints(map);
        burndownRepository.save(burndownDO);
        return burndownDO;
    }
}
