package com.se.scrumflow.dto.req;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class LogCreateReqDTO {

    ObjectId itemId;

    String content;

}