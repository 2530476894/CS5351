package com.se.scrumflow.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "col_burndown")
public class BurndownDO {
    @MongoId
    private ObjectId id;
    private ObjectId sprintId;
    private Date startTime;
    private Date endTime;
    private Map<Date,Integer> actualRemainingStoryPoints;
    private Map<Date,Integer> plannedRemainingStoryPoints;
}