package com.se.scrumflow.dao.entity;

import com.se.scrumflow.dto.req.ActualRemainingStoryPointDTO;
import com.se.scrumflow.dto.req.PlannedRemainingStoryPointDTO;
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
    private int totalStoryPoints;
    private List<ActualRemainingStoryPointDTO> actualRemainingStoryPoints;
    private List<PlannedRemainingStoryPointDTO> plannedRemainingStoryPoints;
}