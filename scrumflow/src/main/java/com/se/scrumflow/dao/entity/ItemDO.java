package com.se.scrumflow.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.se.scrumflow.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "col_item")
public class ItemDO extends BaseDO {

    @MongoId
    @Field("_id")
    ObjectId id;

    ObjectId parentId;

    /**
     * 所属迭代
     */
    ObjectId sprintId;

    String title;

    /**
     * 区分事项类型
     */
    Integer type;

    Integer status;

    Date doneTime;

    String description;

    ObjectId createUser;

    ObjectId assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date startTime;

    Date endTime;

}
