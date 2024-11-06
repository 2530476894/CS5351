package com.se.scrumflow.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPageRespDTO {

    ObjectId id;

    ObjectId parentId;

    ObjectId sprintId;

    String title;

    Integer type;

    Integer status;

    String createUser;

    String assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date startTime;

    Date endTime;

    Date doneTime;

}
