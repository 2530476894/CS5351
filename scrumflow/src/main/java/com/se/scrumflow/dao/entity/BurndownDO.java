package com.se.scrumflow.dao.entity;

import com.se.scrumflow.dto.biz.ActualData;
import com.se.scrumflow.dto.biz.PlanData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
    private List<PlanData> plannedRemainingStoryPoints;
    private List<ActualData> actualRemainingStoryPoints;
    private Integer totalStoryPoints;

}