package com.se.scrumflow.dto.req;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * 一键更新某个事项的单个属性
 */
@Data
public class ItemUpdateFieldReqDTO {

    Integer status;

    Integer type;

    ObjectId createUser;

    ObjectId assignee;

    Integer priority;

    Integer storyPoint;

    String tag;

    Date startTime;

    Date endTime;

}
