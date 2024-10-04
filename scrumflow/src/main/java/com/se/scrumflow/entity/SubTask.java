package com.se.scrumflow.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "sub_tasks")
@Data
public class SubTask {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private int storyPointsForBurndown;
    private Date dueDate;
    private String responsiblePerson;
    private boolean completed;
    private ObjectId taskId;
}