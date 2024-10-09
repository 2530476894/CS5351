package com.se.scrumflow.service;

import com.se.scrumflow.dao.entity.BurndownDO;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BurndownService {
    List<BurndownDO> getBurndownDataForSprint(ObjectId sprintId);
    void logDailyBurndown(ObjectId sprintId, int remainingPoints, int totalPoints, String user);
    List<BurndownDO> getHistoricalBurndownData(ObjectId sprintId, Date startDate, Date endDate);

    Map<Date, Integer> getDailyBurndownSummary(ObjectId sprintId, Date startDate, Date endDate);
    double calculateBurnDownRate(ObjectId sprintId, Date startDate, Date endDate);

    Date predictCompletionDate(ObjectId sprintId, Date currentDate);
}