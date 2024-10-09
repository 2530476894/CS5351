package com.se.scrumflow.controller;

import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.service.BurndownService;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.common.convention.result.Result;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/burndown")
public class BurndownController {

    @Autowired
    private BurndownService burndownService;

    @GetMapping("/{sprintId}")
    public Result<List<BurndownDO>> getBurndownData(@PathVariable("sprintId") String sprintId) {
        List<BurndownDO> burndownData = burndownService.getBurndownDataForSprint(new ObjectId(sprintId));
        return Results.success(burndownData);
    }

    @PostMapping("/{sprintId}")
    public Result<Void> logBurndownData(@PathVariable("sprintId") String sprintId,
                                        @RequestParam int remainingPoints,
                                        @RequestParam int totalPoints,
                                        @RequestParam String user) {
        try {
            burndownService.logDailyBurndown(new ObjectId(sprintId), remainingPoints, totalPoints, user);
            return Results.success();
        } catch (Exception e) {
            return Results.failure();
        }
    }

    @GetMapping("/{sprintId}/history")
    public Result<List<BurndownDO>> getHistoricalData(
            @PathVariable("sprintId") String sprintId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<BurndownDO> historicalData = burndownService.getHistoricalBurndownData(new ObjectId(sprintId), startDate, endDate);
        return Results.success(historicalData);
    }

    @GetMapping("/{sprintId}/daily-summary")
    public Result<Map<Date, Integer>> getDailyBurndownSummary(
            @PathVariable("sprintId") String sprintId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        Map<Date, Integer> dailySummary = burndownService.getDailyBurndownSummary(new ObjectId(sprintId), startDate, endDate);
        return Results.success(dailySummary);
    }


    @GetMapping("/{sprintId}/burn-down-rate")
    public Result<Double> getBurnDownRate(@PathVariable("sprintId") String sprintId,
                                          @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                          @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        double burnDownRate = burndownService.calculateBurnDownRate(new ObjectId(sprintId), startDate, endDate);
        return Results.success(burnDownRate);
    }

    @GetMapping("/{sprintId}/predict-completion")
    public Result<Date> getCompletionPrediction(@PathVariable("sprintId") String sprintId,
                                                @RequestParam("currentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date currentDate) {
        Date predictedDate = burndownService.predictCompletionDate(new ObjectId(sprintId), currentDate);
        return Results.success(predictedDate);
    }
}