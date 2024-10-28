package com.se.scrumflow.service.impl;

import cn.hutool.core.date.DateUtil;
import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.BurndownRepository;
import com.se.scrumflow.dao.repository.SprintRepository;
import com.se.scrumflow.dto.req.ActualRemainingStoryPointDTO;
import com.se.scrumflow.dto.req.PlannedRemainingStoryPointDTO;
import com.se.scrumflow.service.BurndownService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        BurndownDO burndownDO = burndownRepository.findBySprintId(sprintId);
        if (burndownDO == null) {
            Optional<SprintDO> optionalSprint = sprintRepository.findById(sprintId);
            if (optionalSprint.isPresent()) {
                SprintDO sprint = optionalSprint.get();
                // 获取冲刺的开始时间和结束时间
                Date startDate = sprint.getStartDate();
                Date endDate = sprint.getEndDate();
                // 构建查询条件，根据sprintId查询对应的Items
                Query query = new Query(Criteria.where("sprintId").is(sprintId));
                // 使用MongoTemplate执行查询，获取对应的Items列表
                List<ItemDO> items = mongoTemplate.find(query, ItemDO.class);
                // 计算总故事点
                int totalStoryPoints = 0;
                for (ItemDO item : items) {
                    totalStoryPoints += item.getStoryPoint();
                }
                BurndownDO newBurndownDO = new BurndownDO();
                newBurndownDO.setSprintId(sprintId);
                newBurndownDO.setStartTime(startDate);
                newBurndownDO.setEndTime(endDate);
                newBurndownDO.setTotalStoryPoints(totalStoryPoints);
                burndownRepository.save(newBurndownDO);
                return newBurndownDO;
            } else {
                // 添加异常处理，抛出找不到对应SprintDO的异常
                throw new IllegalArgumentException("找不到对应的SprintDO，sprintId: " + sprintId);
            }
        } else {
            return burndownDO;
        }
    }

    @Override
    public BurndownDO initBurndown(ObjectId sprintId) {
        BurndownDO burndownDO = burndownRepository.findBySprintId(sprintId);
        if (burndownDO == null) {
            throw new IllegalArgumentException("找不到对应的BurndownDO，sprintId: " + sprintId);
        }
        List<PlannedRemainingStoryPointDTO> plannedRemainingStoryPointsList = new ArrayList<>();
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
            for (Date currentDate = startDate;!currentDate.after(endDate); currentDate = DateUtil.offsetDay(currentDate, 1)) {
                // 查询计划中当天应该完成的 ItemDO
                Query queryItems = new Query(Criteria
                        .where("sprintId").is(sprintId)
                        .and("plannedEndTime").lte(currentDate).gte(currentDate));

                List<ItemDO> plannedItems = mongoTemplate.find(queryItems, ItemDO.class);
                // 计算当天计划剩余的故事点
                int plannedRemaining = burndownDO.getTotalStoryPoints();
                for (ItemDO item : plannedItems) {
                    plannedRemaining -= item.getStoryPoint();
                }
                // 创建 PlannedRemainingStoryPointDTO 对象并添加到列表中
                PlannedRemainingStoryPointDTO dto = new PlannedRemainingStoryPointDTO(currentDate, plannedRemaining);
                plannedRemainingStoryPointsList.add(dto);
            }
            burndownDO.setPlannedRemainingStoryPoints(plannedRemainingStoryPointsList);
            burndownRepository.save(burndownDO);
            return burndownDO;
        } else {
            throw new IllegalArgumentException("找不到对应的SprintDO，sprintId: " + sprintId);
        }
    }

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
            for (Date currentDate = startDate;!currentDate.after(endDate); currentDate = DateUtil.offsetDay(currentDate, 1)) {
                // 查询计划中当天应该完成的 ItemDO
                Query queryItems = new Query(Criteria
                        .where("sprintId").is(sprintId)
                        .and("status").is(1));

                List<ItemDO> plannedItems = mongoTemplate.find(queryItems, ItemDO.class);
                // 计算当天计划剩余的故事点
                int actualRemaining = burndownDO.getTotalStoryPoints();
                for (ItemDO item : plannedItems) {
                    actualRemaining -= item.getStoryPoint();
                }
                // 创建 PlannedRemainingStoryPointDTO 对象并添加到列表中
                ActualRemainingStoryPointDTO dto = new ActualRemainingStoryPointDTO(currentDate, actualRemaining);
                actualRemainingStoryPointDTOS.add(dto);
            }
            burndownDO.setActualRemainingStoryPoints(actualRemainingStoryPointDTOS);
            burndownRepository.save(burndownDO);
            return burndownDO;
        } else {
            throw new IllegalArgumentException("找不到对应的SprintDO，sprintId: " + sprintId);
        }
    }
}
