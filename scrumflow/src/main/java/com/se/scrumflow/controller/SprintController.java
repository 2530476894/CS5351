package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dto.ItemInsertReqDTO;
import com.se.scrumflow.dto.req.*;
import com.se.scrumflow.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;


@RestController
@RequestMapping("/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping
    public Result<Void> create(@RequestBody SprintCreateReqDTO requestParam) {
        return sprintService.create(requestParam);
    }

    @DeleteMapping
    public Result<Void> delete(@RequestBody SprintDeleteReqDTO requestParam) {
        return sprintService.delete(requestParam);
    }

    @PostMapping("/item")
    public Result<SprintDO> insertItem(@RequestBody ItemInsertReqDTO requestParam) {
        return sprintService.insertItem(requestParam);
    }

    @PutMapping("/item")
    public Result<ItemDO> updateItem(@RequestBody ItemUpdateReqDTO requestParam) {
        return sprintService.updateItem(requestParam);
    }

    @GetMapping("/{sprintId}")
    public Result<SprintWithItemsDTO> getSprintWithItems(@PathVariable("sprintId") ObjectId sprintId) {
        return sprintService.getSprintWithItems(sprintId);
    }
}

