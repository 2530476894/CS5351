package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dto.req.SprintCreateReqDTO;
import com.se.scrumflow.dto.req.SprintUpdateReqDTO;
import com.se.scrumflow.dto.req.SprintWithItemsDTO;
import com.se.scrumflow.service.SprintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sprints")
@Tag(name="Sprint Operations", description="Sprint API")
public class SprintController {

    @Autowired
    private SprintService sprintService;


    /**
     * 创建一个新的冲刺（Sprint）
     *
     * @param requestParam 包含创建冲刺所需参数的DTO对象
     * @return 包含操作结果的Result对象，如果创建成功，则Result对象将表示成功状态；如果创建失败，则Result对象将包含错误信息
     */
    @PostMapping("/create")
    @Operation(summary = "创建空的Sprint",description = "创建一个新的空的Sprint")
    public Result<Void> createSprint(@RequestBody SprintCreateReqDTO requestParam) {
        return sprintService.create(requestParam);
    }

    /**
     * 获取所有冲刺（Sprint）列表
     *
     * @return 包含所有冲刺信息的Result对象，其中SprintDO列表为冲刺的实体数据
     */
    @GetMapping("/all")
    @Operation(summary = "以列表返回所有Sprint",description = "返回所有Sprint的列表")
    public Result<List<SprintDO>> getAllSprints() {
        return sprintService.getAllSprints();
    }


    /**
     * 更新指定的冲刺（Sprint）
     *
     * @param requestParam 包含更新冲刺所需参数的DTO对象
     * @return 包含操作结果的Result对象，如果更新成功，则Result对象将表示成功状态；如果更新失败，则Result对象将包含错误信息
     */
    @PatchMapping("/update")
    @Operation(summary = "更新Sprint",description = "更新指定的Sprint")
    public Result<Void> updateSprint(@RequestBody SprintUpdateReqDTO requestParam) {
        return sprintService.update(requestParam);
    }

    // 删除 Sprint
    @DeleteMapping("/{sprintId}")
    @Operation(summary = "根据Id删除Sprint",description = "根据Id删除指定的Sprint")
    public Result<Void> deleteSprint(@PathVariable("sprintId") ObjectId sprintId) {
        return sprintService.delete(sprintId);
    }

    @GetMapping("/items/{sprintId}")
    @Operation(summary = "返回sprint及其关联的items集合",description = "返回指定的Sprint及其关联的items集合")
    public Result<SprintWithItemsDTO> getSprintWithItems(@PathVariable("sprintId") ObjectId sprintId) {
        return sprintService.getSprintWithItems(sprintId);
    }
}

