package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.dto.req.ItemPageReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateFieldReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateReqDTO;
import com.se.scrumflow.dto.resp.ItemPageRespDTO;
import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import com.se.scrumflow.service.ItemService;
import com.se.scrumflow.utils.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项：用户故事、子需求等统称为事项
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Tag(name = "Item Controller", description = "Item Controller")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/all")
    @Operation(summary = "查询所有事项", description = "查询所有事项")
    public Result<List<ItemQueryRespDTO>> getAllItem() {
        return Results.success(itemService.getAllItem());
    }

    @PostMapping("/create")
    @Operation(summary = "创建单个事项", description = "创建事项")
    public Result<Void> createItem(@RequestBody ItemCreateReqDTO requestParam) {
        itemService.createItem(requestParam);
        return Results.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询单个事项", description = "查询单个事项")
    public Result<ItemQueryRespDTO> queryItem(@PathVariable ObjectId id) {
        return Results.success(itemService.queryItem(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询事项", description = "分页查询事项")
    public Result<Page<ItemPageRespDTO>> pageItem(@ModelAttribute ItemPageReqDTO requestParam) {
        return Results.success(itemService.pageItem(requestParam));
    }

    @PutMapping("/update")
    @Operation(summary = "更新单个事项", description = "更新单个事项")
    public Result<Void> updateItem(@RequestBody ItemUpdateReqDTO requestParam) {
        itemService.updateItem(requestParam);
        return Results.success();
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "一键更新某个事项的单个属性", description = "一键更新某个事项的单个属性")
    public Result<Void> updateItemField(@PathVariable("id") ObjectId id, @RequestBody ItemUpdateFieldReqDTO fields) {
        itemService.updateItemField(id, fields);
        return Results.success();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "逻辑删除单个事项", description = "逻辑删除单个事项")
    public Result<Void> deleteItem(@PathVariable ObjectId id) {
        itemService.logicDeleteItem(id);
        return Results.success();
    }
}
