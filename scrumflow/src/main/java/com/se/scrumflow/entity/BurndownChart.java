package com.se.scrumflow.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "burndown_charts")
@Data
public class BurndownChart {
    @Id
    private ObjectId id;
    private ObjectId sprintId;
    private Date startDate;
    private Date endDate;
    private int totalStoryPoints;
    private int completedStoryPoints;
}
