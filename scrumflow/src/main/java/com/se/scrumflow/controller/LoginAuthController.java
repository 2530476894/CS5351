package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.common.convention.exception.ServiceException;

import org.springframework.web.bind.annotation.*;

import cn.dev33.satoken.stp.StpUtil;

/*
 * 一些问题与代办：
 * 1. 数据库的连接？
 * 2. exception处理
 */

@RestController
@RequestMapping("/auth/login")
public class LoginAuthController {
    
    @PatchMapping("doLogin")
    public Result<Void> doLogin(@RequestParam(value="name") String userName, @RequestParam(value="pwd") String userPwd) {
        //TODO: Get user auth info from db

        String storedPassword = "";
        Integer sessionID = -1;

        try {
            // get record from db by userName
            storedPassword = "";
            sessionID = -1;
        } catch(ServiceException dbException) {
            return Results.failure("123", "Internal Service Error - DB connection");
        } catch (Exception e) {
            return Results.failure("123", "Login Failed. Check if signed up.");
        }
        
        if (userPwd.equals(storedPassword)) {
            StpUtil.login(sessionID);
            return Results.success();
        }
        return Results.failure("123", "Login Failed. Check your name and password");
    }

    @PatchMapping("isLogin")
    public Result<String> isLogin() {
        return Results.success("Logged in: " + StpUtil.isLogin());
    }

    @PatchMapping("tokenInfo")
    public Result<String> tokenInfo() {
        return Results.success(StpUtil.getTokenInfo().toString());
    }
    
    @PatchMapping("logout")
    public Result<Void> logout() {
        StpUtil.logout();
        return Results.success();
    }
}
