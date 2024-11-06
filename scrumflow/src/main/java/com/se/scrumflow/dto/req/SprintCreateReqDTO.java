package com.se.scrumflow.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintCreateReqDTO {
    private String id;
    private String sprintName;
    private Date startDate;
    private Date endDate;
    private String status;
}

