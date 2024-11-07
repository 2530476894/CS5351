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
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

/**
 * 事项：用户故事、子需求等统称为事项
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public Result<Void> createItem(@RequestBody ItemCreateReqDTO requestParam) {
        itemService.createItem(requestParam);
        return Results.success();
    }

    @GetMapping("/{id}")
    public Result<ItemQueryRespDTO> queryItem(@PathVariable ObjectId id) {
        return Results.success(itemService.queryItem(id));
    }

    @GetMapping("/page")
    public Result<Page<ItemPageRespDTO>> pageItem(@RequestBody ItemPageReqDTO requestParam) {
        return Results.success(itemService.pageItem(requestParam));
    }

    @PostMapping("/update")
    public Result<Void> updateItem(@RequestBody ItemUpdateReqDTO requestParam) {
        itemService.updateItem(requestParam);
        return Results.success();
    }

    @PostMapping("/update/{status}")
    public Result<Void> updateItemStatus(@RequestParam("id") ObjectId id, @PathVariable Integer status) {
        itemService.updateItemStatus(id, status);
        return Results.success();
    }

    @PostMapping("/delete/{id}")
    public Result<Void> deleteItem(@PathVariable ObjectId id) {
        itemService.logicDeleteItem(id);
        return Results.success();
    }
}
