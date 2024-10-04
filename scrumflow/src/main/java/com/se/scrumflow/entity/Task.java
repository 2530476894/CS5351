package com.se.scrumflow.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "tasks")
@Data
public class Task {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private int storyPointsForBurndown;
    private Date dueDate;
    private String responsiblePerson;
    private boolean completed;
    private ObjectId userStoryId;
    private List<SubTask> subTasks = new ArrayList<>();
}
