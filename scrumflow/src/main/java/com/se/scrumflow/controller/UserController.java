package com.se.scrumflow.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dao.entity.UserDO;
import com.se.scrumflow.dto.req.UserCreateReqDTO;
import com.se.scrumflow.dto.req.UserLoginReqDTO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;
import com.se.scrumflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller",description = "User related operations")
public class UserController {

    private final UserService userService;
    
    @PostMapping("/doLogin")
    public Result<Void> doLogin(@RequestBody(required=true) UserLoginReqDTO user) {
        userService.doLogin(user.getUsername(), user.getPassword());
        return Results.success();
    }

    @GetMapping("/isLogin")
    public Result<Void> isLogin() {
        userService.isLogin();
        return Results.success();
    }

    @Operation(summary = "Check if logged in", description = "Check if the user is logged in")
    @GetMapping("/checkLogin")
    public Result<Void> checkLogin() {
        userService.checkLogin();
        return Results.success();
    }

    @Operation(summary = "Get logged in user information", description = "Get the user information of the logged in user")
    @GetMapping("/getLogin")
    public Result<UserQueryRespDTO> getLogin() {
        String userID = userService.getLogin();
        return Results.success(BeanUtil.copyProperties(userService.queryUserByID(userID), UserQueryRespDTO.class));
    }
    
    @Operation(summary = "Logout", description = "Logout the user")
    @PostMapping("/logout")
    public Result<Void> logout() {
        userService.logout();
        return Results.success();
    }

    @Operation(summary = "Sign up", description = "Sign up a new user")
    @PostMapping("/signup")
    public Result<Void> signup(@RequestBody UserCreateReqDTO requestParam) {
        userService.createUser(requestParam);
        return Results.success();
    }

    @GetMapping("/getAllUsers")
    public Result<List<UserQueryRespDTO>> getAllUsers() {
        List<UserQueryRespDTO> allUsers = userService.getAllUsers();
        return Results.success(allUsers); 
    }
}
