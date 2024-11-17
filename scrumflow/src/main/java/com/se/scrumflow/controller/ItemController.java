package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.dto.req.ItemPageReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateReqDTO;
import com.se.scrumflow.dto.resp.ItemPageRespDTO;
import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import com.se.scrumflow.service.ItemService;
import com.se.scrumflow.utils.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

/**
 * 事项：用户故事、子需求等统称为事项
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Tag(name = "Item Controller", description = "Item Controller")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    @Operation(summary = "创建单个事项", description = "创建事项")
    public Result<Void> createItem(@RequestBody ItemCreateReqDTO requestParam) {
        StpUtil.checkPermission("item.create");
        itemService.createItem(requestParam);
        return Results.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询单个事项", description = "查询单个事项")
    public Result<ItemQueryRespDTO> queryItem(@PathVariable ObjectId id) {
        StpUtil.checkPermission("item.query");  
        return Results.success(itemService.queryItem(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询事项", description = "分页查询事项")
    public Result<Page<ItemPageRespDTO>> pageItem(@RequestBody ItemPageReqDTO requestParam) {
        StpUtil.checkPermission("item.page");  
        return Results.success(itemService.pageItem(requestParam));
    }

    @PatchMapping("/update")
    @Operation(summary = "更新单个事项", description = "更新单个事项")
    public Result<Void> updateItem(@RequestBody ItemUpdateReqDTO requestParam) {
        StpUtil.checkPermission("item.update");  
        itemService.updateItem(requestParam);
        return Results.success();
    }

    @PatchMapping("/update/{status}")
    @Operation(summary = "更新单个事项状态", description = "更新单个事项状态")
    public Result<Void> updateItemStatus(@RequestParam("id") ObjectId id, @PathVariable Integer status) {
        StpUtil.checkPermission("item.update");  
        itemService.updateItemStatus(id, status);
        return Results.success();
    }

    @PatchMapping("/delete/{id}")
    @Operation(summary = "逻辑删除单个事项", description = "逻辑删除单个事项")
    public Result<Void> deleteItem(@PathVariable ObjectId id) {
        StpUtil.checkPermission("item.delete");  
        itemService.logicDeleteItem(id);
        return Results.success();
    }
}
