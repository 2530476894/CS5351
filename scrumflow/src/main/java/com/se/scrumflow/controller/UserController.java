package com.se.scrumflow.controller;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dto.req.UserCreateReqDTO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;
import com.se.scrumflow.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @PostMapping("/doLogin")
    public Result<Void> doLogin(@RequestParam(value="name") String userName, @RequestParam(value="pwd") String userPwd) {
        userService.doLogin(userName, userPwd);
        return Results.success();
    }

    @GetMapping("/isLogin")
    public Result<Void> isLogin() {
        userService.isLogin();
        return Results.success();
    }

    @GetMapping("/checkLogin")
    public Result<Void> checkLogin() {
        userService.checkLogin();
        return Results.success();
    }

    @GetMapping("/getLogin")
    public Result<UserQueryRespDTO> getLogin() {
        String userID = userService.getLogin();
        return Results.success(userService.queryUserByID(userID).toRespDTO());
    }
    
    @PostMapping("/logout")
    public Result<Void> logout() {
        userService.logout();
        return Results.success();
    }

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
