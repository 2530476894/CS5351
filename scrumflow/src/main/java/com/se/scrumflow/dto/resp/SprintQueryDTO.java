package com.se.scrumflow.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintQueryDTO {

    private String id;
    private String sprintName;
    private Date startDate;
    private Date endDate;
    private Integer status;
    private Integer totalStoryPoints;
    private Integer completedStoryPoints;

}
