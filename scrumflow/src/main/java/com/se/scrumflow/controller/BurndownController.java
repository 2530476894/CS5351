package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.service.BurndownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/burndown")
@Tag(name="burndown", description="burndown api")
public class BurndownController {
    @Autowired
    private BurndownService burndownService;
    /**
     *
     *
     * @param sprintId 冲刺的唯一标识符
     * @return 包含创建的燃尽图信息的Result对象，如果创建成功，则Result对象将包含燃尽图的实体数据；如果创建失败，则Result对象将包含错误信息
     *
     */
    @PostMapping("/{sprintId}")
    @Operation(summary = "根据冲刺ID创建一个空的燃尽图 获取x轴（时间）y轴（故事点数）的数据 当sprint状态改变为进行中的时候触发")
    public Result<BurndownDO> createBurndown(@PathVariable ObjectId sprintId) {
        BurndownDO burndown = burndownService.createBurndown(sprintId);
        return Results.success(burndown);
    }

//    @GetMapping("/init/{sprintId}")
//    @Operation(summary = "初始化燃尽图，填入计划的剩余故事点")
//    public Result<BurndownDO> initBurndown(@PathVariable ObjectId sprintId) {
//        BurndownDO burndown = burndownService.initBurndown(sprintId);
//        return Results.success(burndown);
//    }

    @PutMapping("/update/{sprintId}")
    @Operation(summary = "更新燃尽图，填入实际的剩余故事点  当 Item 状态改变时触发")
    public Result<BurndownDO> updateBurndown(@PathVariable ObjectId sprintId) {
        BurndownDO burndown = burndownService.updateBurndown(sprintId);
        return Results.success(burndown);
    }
}