package com.se.scrumflow.service;

import com.se.scrumflow.dao.entity.UserDO;
import com.se.scrumflow.dto.req.UserCreateReqDTO;

public interface UserService {

    UserDO queryUserByID(String userID);

    UserDO queryUserByUserName(String userName);

    void createUser(UserCreateReqDTO requestParam);

    void doLogin(String userName, String userPwd);

    void isLogin();

    void checkLogin();

    String getLogin();

    void logout();
}