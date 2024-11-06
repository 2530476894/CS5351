package com.se.scrumflow.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemQueryRespDTO {

    String id;

    String parentId;

    String sprintId;

    String title;

    Integer type;

    Integer status;

    String description;

    String createUser;

    String assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date doneTime;

    Date startTime;

    Date endTime;

}
