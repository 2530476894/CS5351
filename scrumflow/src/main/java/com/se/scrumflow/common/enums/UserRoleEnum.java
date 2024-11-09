package com.se.scrumflow.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public enum UserRoleEnum {

    ADMIN("admin", new ArrayList<String>() {{
        add("item.create");
        add("item.query");
        add("item.page");
        add("item.update");
        add("item.delete");
        add("sprint.create");
        add("sprint.update");
    }}),

    PROGRAMMER("programmer", new ArrayList<String>(){{
        add("item.query");
        add("item.page");
        add("item.update");
        add("sprint.update");
    }}),

    PROJECT_OWNER("project_owner", new ArrayList<String>() {{
        add("item.query");
        add("item.page");
    }});

    @Getter
    private final String roleName;

    @Getter
    private final ArrayList<String> permissions;

    public static UserRoleEnum getByRoleName(String roleName) {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null; // TODO or throw an exception
    }

}