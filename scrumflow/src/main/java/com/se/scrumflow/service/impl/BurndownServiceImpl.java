package com.se.scrumflow.service.impl;

import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.BurndownRepository;
import com.se.scrumflow.dao.repository.ItemRepository;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class BurndownServiceImpl implements BurndownService {
@Autowired
private BurndownRepository burndownRepository;
@Autowired
private MongoTemplate mongoTemplate;
@Autowired
private SprintRepository sprintRepository;

    @Override
    public BurndownDO createOrUpdateAndGetBurndownData(ObjectId sprintId) {
        BurndownDO burndownDO = burndownRepository.findBySprintId(sprintId);
        if (burndownDO == null) {
            SprintDO sprint = sprintRepository.findById(sprintId).orElse(null);
            if (sprint!= null) {
                Date startTime = sprint.getStartDate();
                Date endTime = sprint.getEndDate();
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
                newBurndownDO.setStartTime(startTime);
                newBurndownDO.setEndTime(endTime);
                newBurndownDO.setTotalStoryPoints(totalStoryPoints);
                burndownRepository.save(newBurndownDO);
                return newBurndownDO;
            } else {
                // 添加异常处理，抛出找不到对应SprintDO的异常
                throw new IllegalArgumentException("找不到对应的SprintDO，sprintId: " + sprintId);
            }
        } else {
            // 更新burndownDO
            SprintDO sprint = sprintRepository.findById(sprintId).orElse(null);
            if (sprint!= null) {
                // 检查并更新时间属性
                if (sprint.getStartDate()!= null &&!sprint.getStartDate().equals(burndownDO.getStartTime())) {
                    burndownDO.setStartTime(sprint.getStartDate());
                }
                if (sprint.getEndDate()!= null &&!sprint.getEndDate().equals(burndownDO.getEndTime())) {
                    burndownDO.setEndTime(sprint.getEndDate());
                }
                // 重新计算总故事点
                Query query = new Query(Criteria.where("sprintId").is(sprintId));
                List<ItemDO> items = mongoTemplate.find(query, ItemDO.class);
                int newTotalStoryPoints = 0;
                for (ItemDO item : items) {
                    newTotalStoryPoints += item.getStoryPoint();
                }
                burndownDO.setTotalStoryPoints(newTotalStoryPoints);
                // 计算并更新actualRemainingStoryPoints
                List<ActualRemainingStoryPointDTO> actualRemainingStoryPoints = calculateActualRemainingStoryPoints(sprintId,burndownDO);
                burndownDO.setActualRemainingStoryPoints(actualRemainingStoryPoints);

                // 计算并更新plannedRemainingStoryPoints
                List<PlannedRemainingStoryPointDTO> plannedRemainingStoryPoints = calculatePlannedRemainingStoryPoints(sprintId,burndownDO);
                burndownDO.setPlannedRemainingStoryPoints(plannedRemainingStoryPoints);
                burndownRepository.save(burndownDO);
            } else {
                throw new IllegalArgumentException("找不到对应的SprintDO，sprintId: " + sprintId);
            }
            return burndownDO;
        }
    }

    private List<PlannedRemainingStoryPointDTO> calculatePlannedRemainingStoryPoints(ObjectId sprintId,BurndownDO burndownDO) {
        List<PlannedRemainingStoryPointDTO> plannedRemainingStoryPointsList = new ArrayList<>();
        // 获取冲刺的开始时间和结束时间
        SprintDO sprint = mongoTemplate.findById(sprintId, SprintDO.class);
        Date startDate = sprint.getStartDate();
        Date endDate = sprint.getEndDate();
        // 将开始时间和结束时间设置为当天的起始和结束时刻，以便后续比较
        startDate = setToStartOfDay(startDate);
        endDate = setToEndOfDay(endDate);
        // 循环遍历每一天
        while (!startDate.after(endDate)) {
            // 查询计划中当天应该完成的ItemDO
            Query queryItems = new Query(Criteria
                    .where("sprintId").is(sprintId)
                    .and("plannedEndTime").lte(endDate).gte(startDate));

            List<ItemDO> plannedItems = mongoTemplate.find(queryItems, ItemDO.class);
            // 计算当天计划剩余的故事点
            int plannedRemaining = burndownDO.getTotalStoryPoints();
            for (ItemDO item : plannedItems) {
                plannedRemaining -= item.getStoryPoint();
            }
            // 创建PlannedRemainingStoryPointDTO对象并添加到列表中
            PlannedRemainingStoryPointDTO dto = new PlannedRemainingStoryPointDTO(startDate, plannedRemaining);
            plannedRemainingStoryPointsList.add(dto);
            // 将日期推进一天
            startDate = addOneDay(startDate);
        }
        return plannedRemainingStoryPointsList;
    }

    private List<ActualRemainingStoryPointDTO> calculateActualRemainingStoryPoints(ObjectId sprintId,BurndownDO burndownDO) {
        List<ActualRemainingStoryPointDTO> actualRemainingStoryPointsList = new ArrayList<>();
        // 获取冲刺的开始时间和结束时间
        SprintDO sprint = mongoTemplate.findById(sprintId, SprintDO.class);
        Date startDate = sprint.getStartDate();
        Date endDate = sprint.getEndDate();
        // 将开始时间和结束时间设置为当天的起始和结束时刻，以便后续比较
        startDate = setToStartOfDay(startDate);
        endDate = setToEndOfDay(endDate);
        // 循环遍历每一天
        while (!startDate.after(endDate)) {
            // 查询当天状态为已完成的ItemDO
            Query queryItems = new Query(Criteria
                    .where("sprintId").is(sprintId)
                    .and("status").is(1) // 假设1表示已完成状态，根据实际情况调整
                    .and("endTime").lte(endDate).gte(startDate));
            List<ItemDO> completedItems = mongoTemplate.find(queryItems, ItemDO.class);
            // 计算当天实际剩余的故事点
            int actualRemaining = burndownDO.getTotalStoryPoints();
            for (ItemDO item : completedItems) {
                actualRemaining -= item.getStoryPoint();
            }
            // 创建ActualRemainingStoryPointDTO对象并添加到列表中
            ActualRemainingStoryPointDTO dto = new ActualRemainingStoryPointDTO(startDate, actualRemaining);
            actualRemainingStoryPointsList.add(dto);
            // 将日期推进一天
            startDate = addOneDay(startDate);
        }
        return actualRemainingStoryPointsList;
    }


    private Date setToStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    // 将日期设置为当天的结束时刻（23:59:59）
    private Date setToEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    // 将日期推进一天
    private Date addOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}