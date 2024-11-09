package com.se.scrumflow.service.impl;

import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.UserDO;
import com.se.scrumflow.dao.repository.UserRepository;
import com.se.scrumflow.dto.req.UserCreateReqDTO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;
import com.se.scrumflow.service.UserService;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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



}
