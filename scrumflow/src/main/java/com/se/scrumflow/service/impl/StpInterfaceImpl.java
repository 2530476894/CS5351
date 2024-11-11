package com.se.scrumflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.se.scrumflow.dao.entity.UserDO;
import com.se.scrumflow.service.UserService;
import com.se.scrumflow.common.enums.UserRoleEnum;

import cn.dev33.satoken.stp.StpInterface;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StpInterfaceImpl implements StpInterface {

    private final UserService userService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        UserDO userInfo = userService.queryUserByID((String) loginId);
        String userRole = userInfo.getRoleName();
        ArrayList<String> permissionList = UserRoleEnum.getByRoleName(userRole).getPermissions();
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoleList'");
    }
    
}
