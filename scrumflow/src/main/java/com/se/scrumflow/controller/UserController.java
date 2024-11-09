package com.se.scrumflow.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dao.entity.UserDO;
import com.se.scrumflow.dto.req.UserCreateReqDTO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;
import com.se.scrumflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 * 一些问题与代办：
 * 1. exception处理
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller",description = "User related operations")
public class UserController {

    private final UserService userService;
    
    @PatchMapping("/doLogin")
    @Operation(summary = "Login", description = "Login with user name and password")
    public Result<Void> doLogin(@RequestParam(value="name") String userName, @RequestParam(value="pwd") String userPwd) {

        String storedPassword = "";
        String userID = "";

        UserDO userInfo = userService.queryUserByUserName(userName);
        if (userInfo == null) {
            return Results.failure("123", "Login Failed. Check if signed up or your user name.");
        }
        storedPassword = userInfo.getPassword();
        userID = userInfo.getUserID();
        
        if (userPwd.equals(storedPassword)) {
            StpUtil.login(userID);
            return Results.success();
        }
        return Results.failure("123", "Login Failed. Check your name and password");
    }

    @PatchMapping("/isLogin")
    @Operation(summary = "Check if logged in", description = "Check if the user is logged in")
    public Result<Void> isLogin() {
        if (StpUtil.isLogin()) {
            return Results.success();
        }
        return Results.failure("123", "Not logged in");
    }

    @PatchMapping("/checkLogin")
    @Operation(summary = "Check if logged in", description = "Check if the user is logged in")
    public Result<Void> checkLogin() {

        // possible throw: cn.dev33.satoken.exception.NotLoginException
        StpUtil.checkLogin();

        return Results.success();
    }

    @PatchMapping("/getLogin")
    @Operation(summary = "Get logged in user information", description = "Get the user information of the logged in user")
    public Result<UserQueryRespDTO> getLogin() {
        // possible throw: cn.dev33.satoken.exception.NotLoginException
        String userID = StpUtil.getLoginIdAsString();
        return Results.success(userService.queryUserByID(userID).toRespDTO());
    }
    
    @PatchMapping("/logout")
    @Operation(summary = "Logout", description = "Logout the user")
    public Result<Void> logout() {
        StpUtil.logout();
        return Results.success();
    }

    @PatchMapping("/signup")
    @Operation(summary = "Sign up", description = "Sign up a new user")
    public Result<Void> signup(@RequestBody UserCreateReqDTO requestParam) {
        userService.createUser(requestParam);
        return Results.success();
    }
}
