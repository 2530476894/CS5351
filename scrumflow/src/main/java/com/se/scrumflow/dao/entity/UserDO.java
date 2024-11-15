package com.se.scrumflow.dao.entity;

import com.se.scrumflow.common.database.BaseDO;
import com.se.scrumflow.dto.resp.UserQueryRespDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "col_user")
@Data
public class UserDO extends BaseDO {

    @MongoId
    @Field("_id")
    ObjectId id;

    String userID;

    String username;

    String password;

    String roleName;
}
