package com.se.scrumflow.dto.req;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
public class ItemPageReqDTO {

    ObjectId sprintId;

    Integer type;

    Integer status;

    ObjectId createUser;

    ObjectId assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date startTime;

    Date endTime;

    Integer pageNumber;

    Integer pageSize;

}
