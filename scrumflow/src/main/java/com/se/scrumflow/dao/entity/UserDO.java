package com.se.scrumflow.dao.entity;

import com.se.scrumflow.common.database.BaseDO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "col_user")
@Data
public class UserDO extends BaseDO {

    ObjectId id;

    String userID;

    String username;

    String password;

    String roleName;

    public UserQueryRespDTO toRespDTO() {
        return new UserQueryRespDTO(userID, username, roleName);
    }
}
