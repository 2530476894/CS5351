package com.se.scrumflow.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "user_stories")
@Data
public class UserStory {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private int storyPointsForBurndown;
    private int storyPoints;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String assignedTo;
    private ObjectId assignedRoleId;
    private String sprint;
    private int priority;
    private List<String> acceptanceCriteria;
    private List<Task> tasks = new ArrayList<>();
}
// This class represents a user story in the scrum flow application. It has the following attributes:
// - id: the unique identifier of the user story
