package com.se.scrumflow.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "sprints")
@Data
public class Sprint {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<UserStory> userStories = new ArrayList<>();
    private Project project;
}