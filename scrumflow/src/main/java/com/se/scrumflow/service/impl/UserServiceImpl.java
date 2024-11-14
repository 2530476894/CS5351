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
import lombok.RequiredArgsConstructor;

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
        Optional<UserDO> optionalUserDO = userRepository.findByUserID(userID);
        if (!optionalUserDO.isPresent()) {
            return null;
        }
        return optionalUserDO.get();
    }

    @Override
    public UserDO queryUserByUserName(String userName) {
        Optional<UserDO> optionalUserDO = userRepository.findByUserName(userName);
        if (!optionalUserDO.isPresent()) {
            return null;
        }
        return optionalUserDO.get();
    }

    @Override
    public void createUser(UserCreateReqDTO requestParam) {
        UserDO userDo = BeanUtil.copyProperties(requestParam, UserDO.class);
        userRepository.insert(userDo);
    }

    @Override
    public void doLogin(String userName, String userPwd) {
        String storedPassword = "";
        String userID = "";

        Optional<UserDO> optionalUserDO = userRepository.findByUserName(userName);
        if (!optionalUserDO.isPresent()) {
            throw new ClientException("Login Failed. Check if signed up or your user name.");
        }
        UserDO userInfo = optionalUserDO.get();

        storedPassword = userInfo.getPassword();
        userID = userInfo.getUserID();
        
        if (userPwd.equals(storedPassword)) {
            StpUtil.login(userID);
        }
        throw new ClientException("Login Failed. Check if signed up or your user name.");
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
}
