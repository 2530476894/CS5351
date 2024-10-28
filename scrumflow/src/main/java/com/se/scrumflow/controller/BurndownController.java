package com.se.scrumflow.controller;

import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.service.BurndownService;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.common.convention.result.Result;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/burndown")
public class BurndownController {

    @Autowired
    private BurndownService burndownService;

    @PostMapping("/{sprintId}")
    public Result<BurndownDO> createBurndown(@PathVariable ObjectId sprintId) {
        BurndownDO burndown = burndownService.createBurndown(sprintId);
        return Results.success(burndown);
    }

    @GetMapping("/init/{sprintId}")
    public Result<BurndownDO> initBurndown(@PathVariable ObjectId sprintId) {
        BurndownDO burndown = burndownService.initBurndown(sprintId);
        return Results.success(burndown);
    }

    @PutMapping("/update/{sprintId}")
    public Result<BurndownDO> updateBurndown(@PathVariable ObjectId sprintId) {
        BurndownDO burndown = burndownService.updateBurndown(sprintId);
        return Results.success(burndown);
    }
}