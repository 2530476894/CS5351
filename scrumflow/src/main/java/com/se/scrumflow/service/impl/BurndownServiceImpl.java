package com.se.scrumflow.service.impl;

import cn.hutool.core.date.DateUtil;
import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.BurndownRepository;
import com.se.scrumflow.dao.repository.SprintRepository;
import com.se.scrumflow.dto.req.ActualRemainingStoryPointDTO;
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

        Map<Date, Integer> map = new HashMap<>();
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
//        for (Date currentDate = startDate.; !currentDate.after(endDate); currentDate = DateUtil.offsetDay(currentDate, 1)) {
//            Date startOfDay = DateUtil.beginOfDay(currentDate);  // 当前日期的开始时刻
//            Date endOfDay = DateUtil.endOfDay(currentDate);      // 当前日期的结束时刻
//            Query queryItems = new Query(Criteria
//                    .where("sprintId").is(sprintId)
//                    .and("endTime").lte(endOfDay).gte(startOfDay));
//            List<ItemDO> plannedItems = mongoTemplate.find(queryItems, ItemDO.class);
//
//        }
//        Query query = new Query(Criteria.where("sprintId").is(sprintId));
//        List<ItemDO> items = mongoTemplate.find(query, ItemDO.class);
//        //mapToInt 是一个中间操作，它将流中的每个元素（在这里是 ItemDO 对象）映射为一个 int 值。
//        int totalStoryPoints = items.stream().mapToInt(ItemDO::getStoryPoint).sum();
        // 创建并保存新的BurndownDO
        BurndownDO newBurndownDO = new BurndownDO();
        newBurndownDO.setSprintId(sprintId);
        newBurndownDO.setStartTime(startDate);
        newBurndownDO.setEndTime(endDate);
        newBurndownDO.setPlannedRemainingStoryPoints(map);
        burndownRepository.insert(newBurndownDO);
        return newBurndownDO;
    }


//    @Override
//    public BurndownDO initBurndown(ObjectId sprintId) {
//        BurndownDO burndownDO = burndownRepository.findBySprintId(sprintId);
//        List<PlannedRemainingStoryPointDTO> plannedRemainingStoryPointsList = new ArrayList<>();
//        // 获取冲刺的开始时间和结束时间
//        Optional<SprintDO> optionalSprint = Optional.ofNullable(mongoTemplate.findById(sprintId, SprintDO.class));
//        if (optionalSprint.isPresent()) {
//            SprintDO sprint = optionalSprint.get();
//            Date startDate = sprint.getStartDate();
//            Date endDate = sprint.getEndDate();
//            // 将开始时间和结束时间设置为当天的起始和结束时刻
//            startDate = DateUtil.beginOfDay(startDate);
//            endDate = DateUtil.endOfDay(endDate);
//            // 循环遍历每一天
//            for (Date currentDate = startDate; !currentDate.after(endDate); currentDate = DateUtil.offsetDay(currentDate, 1)) {
//                // 查询计划中当天应该完成的 ItemDO
//                Date startOfDay = DateUtil.beginOfDay(currentDate);  // 当前日期的开始时刻
//                Date endOfDay = DateUtil.endOfDay(currentDate);      // 当前日期的结束时刻
//                Query queryItems = new Query(Criteria
//                        .where("sprintId").is(sprintId)
//                        .and("endTime").lte(endOfDay).gte(startOfDay));
//                List<ItemDO> plannedItems = mongoTemplate.find(queryItems, ItemDO.class);
//                // 计算当天计划剩余的故事点
//                int plannedRemaining = sprint.getTotalStoryPoints();
//                for (ItemDO item : plannedItems) {
//                    plannedRemaining -= item.getStoryPoint();
//                }
//                // 创建 PlannedRemainingStoryPointDTO 对象并添加到列表中
//                PlannedRemainingStoryPointDTO dto = new PlannedRemainingStoryPointDTO(currentDate, plannedRemaining);
//                plannedRemainingStoryPointsList.add(dto);
//            }
//            burndownDO.setPlannedRemainingStoryPoints(plannedRemainingStoryPointsList);
//            burndownRepository.save(burndownDO);
//            return burndownDO;
//        } else {
//            throw new IllegalArgumentException("找不到对应的SprintDO，sprintId: " + sprintId);
//        }
//    }

    @Override
    public BurndownDO updateBurndown(ObjectId sprintId) {
        BurndownDO burndownDO = burndownRepository.findBySprintId(sprintId);
        if (burndownDO == null) {
            throw new IllegalArgumentException("找不到对应的BurndownDO，sprintId: " + sprintId);
        }
        List<ActualRemainingStoryPointDTO> actualRemainingStoryPointDTOS = new ArrayList<>();
        // 获取冲刺的开始时间和结束时间
        Optional<SprintDO> optionalSprint = Optional.ofNullable(mongoTemplate.findById(sprintId, SprintDO.class));
        if (optionalSprint.isPresent()) {
            SprintDO sprint = optionalSprint.get();
            Date startDate = sprint.getStartDate();
            Date endDate = sprint.getEndDate();
            // 将开始时间和结束时间设置为当天的起始和结束时刻
            startDate = DateUtil.beginOfDay(startDate);
            endDate = DateUtil.endOfDay(endDate);
            // 循环遍历每一天
            for (Date currentDate = startDate; !currentDate.after(endDate); currentDate = DateUtil.offsetDay(currentDate, 1)) {

                // 查询计划中当天应该完成的 ItemDO
                Query queryItems = new Query(Criteria
                        .where("sprintId").is(sprintId)
                        .and("status").is(1));

                List<ItemDO> plannedItems = mongoTemplate.find(queryItems, ItemDO.class);
                // 计算当天计划剩余的故事点
                int actualRemaining = sprint.getTotalStoryPoints();
                for (ItemDO item : plannedItems) {
                    actualRemaining -= item.getStoryPoint();
                }
                // 创建 PlannedRemainingStoryPointDTO 对象并添加到列表中
                ActualRemainingStoryPointDTO dto = new ActualRemainingStoryPointDTO(currentDate, actualRemaining);
                actualRemainingStoryPointDTOS.add(dto);
            }
//            burndownDO.setActualRemainingStoryPoints(actualRemainingStoryPointDTOS);
            burndownRepository.save(burndownDO);
            return burndownDO;
        } else {
            throw new IllegalArgumentException("找不到对应的SprintDO，sprintId: " + sprintId);
        }
    }
}
