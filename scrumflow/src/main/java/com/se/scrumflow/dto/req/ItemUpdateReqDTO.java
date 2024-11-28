package com.se.scrumflow.dto.req;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * 更新item信息，不涉及更新状态
 */
@Data
public class ItemUpdateReqDTO {

    ObjectId id;

    ObjectId parentId;

    ObjectId sprintId;

    String title;

    String description;

}
