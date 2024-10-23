package com.se.scrumflow.dao.entity;

import com.se.scrumflow.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.Date;
import java.util.List;

@Document(collection = "col_sprint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintDO extends BaseDO {
    @MongoId
    @Field("_id")
    private ObjectId id;
    private String sprintName;
    private Date startDate;
    private Date endDate;
    private String status;
    private int totalStoryPoints;
    private int completedStoryPoints;
}