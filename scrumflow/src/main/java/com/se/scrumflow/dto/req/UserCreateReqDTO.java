package com.se.scrumflow.dto.req;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UserCreateReqDTO {
    ObjectId id;

    String userID;

    String username;

    String password;

    String roleName;
    
}
