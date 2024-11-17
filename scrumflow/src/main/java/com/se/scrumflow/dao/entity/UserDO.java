package com.se.scrumflow.dao.entity;

import com.se.scrumflow.common.database.BaseDO;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "col_user")
@Data
public class UserDO extends BaseDO {

    private ObjectId id;

    private String username;

    private String password;

}
