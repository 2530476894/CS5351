package com.se.scrumflow.service.impl;

import com.se.scrumflow.common.convention.exception.ClientException;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.UserDO;
import com.se.scrumflow.dao.repository.UserRepository;
import com.se.scrumflow.dto.req.UserCreateReqDTO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;
import com.se.scrumflow.service.UserService;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.dev33.satoken.stp.StpUtil;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public UserDO queryUserByID(String userID) {
        List<UserDO> optionalUserDO = userRepository.findByuserID(userID);
        if (optionalUserDO.isEmpty()) {
            return null;
        }
        return optionalUserDO.get(0);
    }

    @Override
    public UserDO queryUserByUserName(String userName) {
        List<UserDO> optionalUserDO = userRepository.findByusername(userName);
        if (optionalUserDO.isEmpty()) {
            return null;
        }
        return optionalUserDO.get(0);
    }

    @Override
    public void createUser(UserCreateReqDTO requestParam) {
        requestParam.setUserID(UUID.randomUUID().toString());
        UserDO userDo = BeanUtil.copyProperties(requestParam, UserDO.class);
        userRepository.insert(userDo);
    }

    @Override
    public void doLogin(String userName, String userPwd) {
        String storedPassword = "";
        String userID = "";

        List<UserDO> optionalUserDO = userRepository.findByusername(userName);
        if (optionalUserDO.isEmpty()) {
            throw new ClientException("Login Failed. Check if signed up or your user name.1");
        }
        UserDO userInfo = optionalUserDO.get(0);

        storedPassword = userInfo.getPassword();
        userID = userInfo.getUserID();
        
        if (userPwd.equals(storedPassword)) {
            StpUtil.login(userID);
        } else {
            throw new ClientException("Login Failed. Check if signed up or your user name.2");
        }
    }

    @Override
    public void isLogin() {
        if (!StpUtil.isLogin()) {
            throw new ClientException("User have not logged in.");
        }
    }

    @Override
    public void checkLogin() {
        try {
            StpUtil.checkLogin();
        } catch (NotLoginException e) {
            throw new ClientException("User have not logged in.");
        }
    }

    @Override
    public String getLogin() {
        try {
            String userID = StpUtil.getLoginIdAsString();
            return userID;
        } catch (NotLoginException e) {
            throw new ClientException("User have not logged in.");
        }
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public List<UserQueryRespDTO> getAllUsers() {
        List<UserQueryRespDTO> userDTOs = new ArrayList<>();
        List<UserDO> userDOs = userRepository.findAll();
        for (UserDO userDO : userDOs) {
            userDTOs.add(BeanUtil.copyProperties(userDO, UserQueryRespDTO.class));
        }
        return userDTOs;
    }
}
