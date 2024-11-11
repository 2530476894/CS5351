package com.se.scrumflow.dto.req;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
public class ItemPageReqDTO {

    ObjectId sprintId;

    Integer type;

    Integer status;

    String createUser;

    String assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date startTime;

    Date endTime;

    int pageNumber;

    int pageSize;

}
