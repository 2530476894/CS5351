package com.se.scrumflow.dto.req;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
public class ItemCreateReqDTO {

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

    String description;

    ObjectId createUser;

    ObjectId assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date startTime;

    Date endTime;

    Date doneTime;

}
