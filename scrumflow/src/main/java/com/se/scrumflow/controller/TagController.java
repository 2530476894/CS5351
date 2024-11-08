package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dto.req.TagCreateReqDTO;
import com.se.scrumflow.dto.resp.TagQueryRespDTO;
import com.se.scrumflow.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/create")
    public Result<Void> createTag(@RequestBody TagCreateReqDTO requestParam) {
        tagService.createTag(requestParam);
        return Results.success();
    }

    @GetMapping("/all")
    public Result<TagQueryRespDTO> getAllTag() {
        return Results.success(tagService.getAllTag());
    }

}
