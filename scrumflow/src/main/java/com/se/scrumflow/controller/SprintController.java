package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dto.req.*;
import com.se.scrumflow.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;

import java.util.List;


@RestController
@RequestMapping("/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;
    // 创建 Sprint
    @PostMapping
    public Result<Void> createSprint(@RequestBody SprintCreateReqDTO requestParam) {
        return sprintService.create(requestParam);
    }
    @GetMapping
    public Result<List<SprintDO>> getAllSprints() {
        return sprintService.getAllSprints();
    }
    // 更新 Sprint
    @PutMapping("/{sprintId}")
    public Result<Void> updateSprint(@PathVariable("sprintId") ObjectId sprintId,
                                     @RequestBody SprintUpdateReqDTO requestParam) {
        return sprintService.update(sprintId, requestParam);
    }
    // 删除 Sprint
    @DeleteMapping("/{sprintId}")
    public Result<Void> deleteSprint(@PathVariable("sprintId") ObjectId sprintId) {
        return sprintService.delete(sprintId);
    }
    @GetMapping("/{sprintId}/items")
    public Result<SprintWithItemsDTO> getSprintWithItems(@PathVariable("sprintId") ObjectId sprintId) {
        return sprintService.getSprintWithItems(sprintId);
    }
}

