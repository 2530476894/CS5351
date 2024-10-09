package com.se.scrumflow.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "col_burndown")
public class BurndownDO {
    @MongoId
    @Field("_id")
    private ObjectId id;                // Unique identifier for the burndown record

    @Field("sprint_id")
    private ObjectId sprintId;           // Reference to the associated sprint

    @Field("date")
    private Date date;                   // Date of the log entry

    @Field("remaining_story_points")
    private int remainingStoryPoints;    // Remaining story points at the end of the day

    @Field("total_story_points")
    private int totalStoryPoints;        // Total story points at the start of the sprint

    @Field("created_at")
    private Date createdAt;              // Timestamp for when the entry was created

    @Field("updated_at")
    private Date updatedAt;              // Timestamp for when the entry was last updated

    @Field("updated_by")
    private String updatedBy;            // User who last updated the entry
}