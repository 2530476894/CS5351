package com.se.scrumflow.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "col_log")
public class LogDO {

    @MongoId
    @Field("_id")
    ObjectId id;

    ObjectId itemId;

    String content;

    @CreatedDate
    @Field("createTime")
    Date createTime;

}
