package com.se.scrumflow.dto.resp;

import com.se.scrumflow.dto.biz.PlanData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BurndownPlanDataRespDTO {

    private List<PlanData> plannedRemainingStoryPoints;

}
