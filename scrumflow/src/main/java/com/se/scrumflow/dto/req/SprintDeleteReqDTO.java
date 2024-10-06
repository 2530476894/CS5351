package com.se.scrumflow.dto.req;

import org.bson.types.ObjectId;

public class SprintDeleteReqDTO {
    private ObjectId sprintId;

    public ObjectId getSprintId() {
        return sprintId;
    }

    public void setSprintId(ObjectId sprintId) {
        this.sprintId = sprintId;
    }
}

