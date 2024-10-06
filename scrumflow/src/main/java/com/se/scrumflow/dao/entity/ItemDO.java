package com.se.scrumflow.dao.entity;

import com.se.scrumflow.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "col_item")
public class ItemDO extends BaseDO {

    @MongoId
    @Field("_id")
    ObjectId id;

    String parentId;

    /**
     * 所属迭代
     */
    String sprintId;

    String title;

    /**
     * 区分事项类型
     */
    Integer type;

    Integer status;

    String description;

    ObjectId createUser;

    ObjectId assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date deadLine;

    List<LogDO> logs = new ArrayList<>();

}
