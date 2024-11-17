package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.dto.req.SprintCreateReqDTO;
import com.se.scrumflow.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sprint")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @PostMapping("/create")
    public Result<Void> create(@RequestBody SprintCreateReqDTO requestParam) {
        return null;
    }


    @PutMapping("/update")
    public Result<Void> update() {
        return null;
    }

}

