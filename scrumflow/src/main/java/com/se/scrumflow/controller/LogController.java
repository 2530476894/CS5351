package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dto.req.LogCreateReqDTO;
import com.se.scrumflow.dto.req.LogPageReqDTO;
import com.se.scrumflow.dto.resp.LogPageRespDTO;
import com.se.scrumflow.service.LogService;
import com.se.scrumflow.utils.Page;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PostMapping("/create")
    public Result<Void> createLog(@RequestBody LogCreateReqDTO requestParam) {
        StpUtil.checkPermission("log.create");  
        logService.createLog(requestParam);
        return Results.success();
    }

    @GetMapping("/page")
    public Result<Page<LogPageRespDTO>> pageLog(@RequestBody LogPageReqDTO requestParam) {
        StpUtil.checkPermission("log.page");  
        return Results.success(logService.pageLog(requestParam));
    }

}
