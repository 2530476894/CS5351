package com.se.scrumflow.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.se.scrumflow.common.convention.exception.ClientException;
import com.se.scrumflow.common.convention.exception.ServiceException;
import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.BurndownRepository;
import com.se.scrumflow.dao.repository.SprintRepository;
import com.se.scrumflow.dto.biz.ActualData;
import com.se.scrumflow.dto.biz.PlanData;
import com.se.scrumflow.dto.resp.BurndownActualDataRespDTO;
import com.se.scrumflow.dto.resp.BurndownPlanDataRespDTO;
import com.se.scrumflow.service.BurndownService;
import com.se.scrumflow.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

import static com.se.scrumflow.common.enums.ItemStatusEnum.DONE;

@Service
@RequiredArgsConstructor
public class BurndownServiceImpl implements BurndownService {

    private final SprintRepository sprintRepository;

    private final MongoTemplate mongoTemplate;

    private final BurndownRepository burndownRepository;

    @Override
    public BurndownPlanDataRespDTO generateBurndownPlanData(ObjectId id) {
        BurndownDO burndown = burndownRepository.findBySprintId(id);
        if (burndown != null) {
            return BurndownPlanDataRespDTO.builder()
                    .plannedRemainingStoryPoints(burndown.getPlannedRemainingStoryPoints())
                    .build();
        }
        Optional<SprintDO> optionalSprintDO = sprintRepository.findById(id);
        SprintDO sprintDO;
        try {
            sprintDO = optionalSprintDO.orElseThrow((Supplier<Exception>) RuntimeException::new);
        } catch (Exception re) {
            throw new ServiceException("This sprint does NOT exist.");
        }
        Date startDate = sprintDO.getStartDate();
        Date endDate = sprintDO.getEndDate();
        List<Date> planDateRange = DateUtils.getDateList(startDate, endDate);
        // 根据【Item.endTime】确定计划剩余故事点折线的数据点
        Query queryItems = new Query(
                Criteria.where("sprintId").is(id).and("endTime").gte(startDate).lte(endDate)
        );
        int planTotalStoryPoints = mongoTemplate.find(queryItems, ItemDO.class).stream().mapToInt(ItemDO::getStoryPoint).sum();
        List<PlanData> planDataList = new ArrayList<>(planDateRange.size());
        planDataList.add(
                PlanData.builder()
                        .date(startDate)
                        .planPoints(planTotalStoryPoints)
                        .build()
        );
        for (int i = 1; i < planDateRange.size(); i++) {
            Date currentDate = planDateRange.get(i);
            Date startOfDay = DateUtil.beginOfDay(currentDate);
            Date endOfDay = DateUtil.endOfDay(currentDate);
            Query query = new Query(
                    Criteria.where("sprintId").is(id).and("endTime").gte(startOfDay).lte(endOfDay)
            );
            List<ItemDO> planItems = mongoTemplate.find(query, ItemDO.class);
            int planCurrentDateStoryPoints = planItems.stream().mapToInt(ItemDO::getStoryPoint).sum();
            Integer yesterdayStoryPoints = planDataList.get(i - 1).getPlanPoints();
            planDataList.add(
                    PlanData.builder()
                            .date(currentDate)
                            .planPoints(yesterdayStoryPoints - planCurrentDateStoryPoints)
                            .build()
            );
        }
        BurndownPlanDataRespDTO burndownPlanDataRespDTO = BurndownPlanDataRespDTO.builder()
                .plannedRemainingStoryPoints(planDataList)
                .build();
        // 【注意】计划剩余故事点折线和实际剩余故事点折线的第一个点一定是相同的
        List<ActualData> actualDataList = List.of(
                ActualData.builder()
                        .date(startDate)
                        .actualPoints(planTotalStoryPoints)
                        .build()
        );
        BurndownDO burndownDO = BurndownDO.builder()
                .sprintId(id)
                .totalStoryPoints(planTotalStoryPoints)
                .actualRemainingStoryPoints(actualDataList)
                .plannedRemainingStoryPoints(planDataList)
                .build();
        burndownRepository.save(burndownDO);
        return burndownPlanDataRespDTO;
    }

    @Override
    public BurndownActualDataRespDTO generateBurndownActualData(ObjectId id) {
        BurndownDO burndownDO = burndownRepository.findBySprintId(id);
        if (burndownDO == null) {
            throw new ClientException("Please init Burn Down Chart first and make sure this sprint exists");
        }
        List<ActualData> actualRemainingStoryPoints = burndownDO.getActualRemainingStoryPoints();
        ActualData last = actualRemainingStoryPoints.get(actualRemainingStoryPoints.size() - 1);
        Date now = new Date();
        if (!now.after(last.getDate())) {
            return BurndownActualDataRespDTO.builder()
                    .actualRemainingStoryPoints(burndownDO.getActualRemainingStoryPoints())
                    .build();
        }
        List<Date> dateList = DateUtils.getDateList(last.getDate(), now);
        for (int i = 1; i < dateList.size(); i++) {
            Date date = dateList.get(i);
            int lastPoints = last.getActualPoints();
            Date startOfDay = DateUtil.beginOfDay(date);
            Date endOfDay = DateUtil.endOfDay(date);
            Query query = new Query(
                    Criteria
                            .where("sprintId").is(id)
                            .and("doneTime").gte(startOfDay).lte(endOfDay)
            );
            List<ItemDO> doneItems = mongoTemplate.find(query, ItemDO.class);
            int donePoints = doneItems.stream().mapToInt(ItemDO::getStoryPoint).sum();
            actualRemainingStoryPoints.add(
                    ActualData.builder()
                            .date(date)
                            .actualPoints(lastPoints - donePoints)
                            .build()
            );
            last = actualRemainingStoryPoints.get(actualRemainingStoryPoints.size() - 1);
        }
        burndownDO.setActualRemainingStoryPoints(actualRemainingStoryPoints);
        burndownRepository.save(burndownDO);
        return BurndownActualDataRespDTO.builder()
                .actualRemainingStoryPoints(actualRemainingStoryPoints)
                .build();
    }

}
