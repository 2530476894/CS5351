package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.service.ItemService;
import lombok.RequiredArgsConstructor;
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

}
