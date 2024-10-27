package com.se.scrumflow.controller;

import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.service.BurndownService;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.common.convention.result.Result;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/burndown")
public class BurndownController {

    @Autowired
    private BurndownService burndownService;

    @GetMapping("/{sprintId}")
    public Result<BurndownDO> getBurndownData(@PathVariable("sprintId") String sprintId) {
       BurndownDO burndownData = burndownService.createOrUpdateAndGetBurndownData(new ObjectId(sprintId));
       return Results.success(burndownData);
    }
}