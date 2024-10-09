package com.se.scrumflow.service.impl;

import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.dao.repository.BurndownRepository;
import com.se.scrumflow.service.BurndownService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BurndownServiceImpl implements BurndownService {

    @Autowired
    private BurndownRepository burndownRepository;

    @Override
    public List<BurndownDO> getBurndownDataForSprint(ObjectId sprintId) {
        return burndownRepository.findBySprintId(sprintId);
    }

    @Override
    public void logDailyBurndown(ObjectId sprintId, int remainingPoints, int totalPoints, String user) {
        BurndownDO burndown = BurndownDO.builder()
                .sprintId(sprintId)
                .date(new Date())
                .remainingStoryPoints(remainingPoints)
                .totalStoryPoints(totalPoints)
                .createdAt(new Date())
                .updatedAt(new Date())
                .updatedBy(user)
                .build();
        burndownRepository.save(burndown);
    }

    @Override
    public List<BurndownDO> getHistoricalBurndownData(ObjectId sprintId, Date startDate, Date endDate) {
        return burndownRepository.findBySprintIdAndDateBetween(sprintId, startDate, endDate);
    }


    @Override
    public Map<Date, Integer> getDailyBurndownSummary(ObjectId sprintId, Date startDate, Date endDate) {
        List<BurndownDO> burndownData = burndownRepository.findBySprintIdAndDateBetween(sprintId, startDate, endDate);

        Map<Date, Integer> dailySummary = new LinkedHashMap<>();
        for (BurndownDO burndown : burndownData) {
            dailySummary.put(burndown.getDate(), burndown.getRemainingStoryPoints());
        }
        return dailySummary;
    }

    @Override
    public double calculateBurnDownRate(ObjectId sprintId, Date startDate, Date endDate) {
        List<BurndownDO> burndownData = burndownRepository.findBySprintIdAndDateBetween(sprintId, startDate, endDate);

        if (burndownData.isEmpty()) {
            return 0.0;
        }

        int totalPoints = burndownData.get(0).getTotalStoryPoints();
        int remainingPoints = burndownData.get(burndownData.size() - 1).getRemainingStoryPoints();
        long daysPassed = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());

        return (double) (totalPoints - remainingPoints) / daysPassed;
    }

    @Override
    public Date predictCompletionDate(ObjectId sprintId, Date currentDate) {
        List<BurndownDO> burndownData = burndownRepository.findBySprintId(sprintId);

        if (burndownData.isEmpty()) {
            return null; // 或抛出异常
        }

        int remainingPoints = burndownData.get(burndownData.size() - 1).getRemainingStoryPoints();
        double burnDownRate = calculateBurnDownRate(sprintId, burndownData.get(0).getDate(), currentDate); // 传入适当的日期范围

        if (burnDownRate <= 0) {
            return null; // 或者表示无法完成
        }

        long daysToComplete = (long) (remainingPoints / burnDownRate);
        return Date.from(currentDate.toInstant().plus(daysToComplete, ChronoUnit.DAYS));
    }


}