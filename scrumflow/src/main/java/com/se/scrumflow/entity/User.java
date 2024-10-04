package com.se.scrumflow.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private ObjectId id;
    private String username;
    private String password;
    // 使用角色枚举列表代替之前的角色标识符列表
    private List<ScrumRole> roles = new ArrayList<>();
}
