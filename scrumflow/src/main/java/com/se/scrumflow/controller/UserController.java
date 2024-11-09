package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dao.entity.UserDO;
import com.se.scrumflow.common.convention.exception.ServiceException;
import com.se.scrumflow.dto.req.UserCreateReqDTO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;
import com.se.scrumflow.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;

/*
 * 一些问题与代办：
 * 1. exception处理
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @PatchMapping("/doLogin")
    public Result<Void> doLogin(@RequestParam(value="name") String userName, @RequestParam(value="pwd") String userPwd) {

        String storedPassword = "";
        String userID = "";

        try {
            UserDO userInfo = userService.queryUserByUserName(userName);
            storedPassword = userInfo.getPassword();
            userID = userInfo.getUserID();
        } catch (Exception e) {
            // TODO
            return Results.failure("123", "Login Failed. Check if signed up.");
        }
        
        if (userPwd.equals(storedPassword)) {
            StpUtil.login(userID);
            return Results.success();
        }
        return Results.failure("123", "Login Failed. Check your name and password");
    }

    @PatchMapping("/isLogin")
    public Result<Void> isLogin() {
        if (StpUtil.isLogin()) {
            return Results.success();
        }
        return Results.failure("123", "Not logged in");
    }

    @PatchMapping("/checkLogin")
    public Result<Void> checkLogin() {

        // possible throw: cn.dev33.satoken.exception.NotLoginException
        StpUtil.checkLogin();

        return Results.success();
    }

    @PatchMapping("/getLogin")
    public Result<UserQueryRespDTO> getLogin() {
        // possible throw: cn.dev33.satoken.exception.NotLoginException
        String userID = StpUtil.getLoginIdAsString();
        return Results.success(userService.queryUserByID(userID).toRespDTO());
    }
    
    @PatchMapping("/logout")
    public Result<Void> logout() {
        StpUtil.logout();
        return Results.success();
    }

    @PatchMapping("/signup")
    public Result<Void> signup(@RequestBody UserCreateReqDTO requestParam) {
        userService.createUser(requestParam);
        return Results.success();
    }
}
