package com.se.scrumflow.dto.req;

import lombok.Data;

import java.util.Date;

@Data
public class ItemCreateReqDTO {

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

    String createUser;

    String assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date startTime;

    Date endTime;

}
