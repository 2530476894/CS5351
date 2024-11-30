package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dto.resp.BurndownActualDataRespDTO;
import com.se.scrumflow.dto.resp.BurndownPlanDataRespDTO;
import com.se.scrumflow.service.BurndownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/burndown")
@RequiredArgsConstructor
@Tag(name = "burndown", description = "burndown api")
public class BurndownController {

    private final BurndownService burndownService;

    @GetMapping("/plan/{id}")
    @Operation(summary = "根据冲刺ID初始化计划燃尽图 获取计划中的剩余故事点的数据,sprint初始化时创建")
    public Result<BurndownPlanDataRespDTO> generateBurndownPlanData(@PathVariable ObjectId id) {
        return Results.success(burndownService.generateBurndownPlanData(id));
    }

    @GetMapping("/actual/{id}")
    @Operation(summary = "根据冲刺ID初始化计划燃尽图 获取计划中的剩余故事点的数据,sprint初始化时创建")
    public Result<BurndownActualDataRespDTO> generateBurndownActualData(@PathVariable ObjectId id) {
        return Results.success(burndownService.generateBurndownActualData(id));
    }

}