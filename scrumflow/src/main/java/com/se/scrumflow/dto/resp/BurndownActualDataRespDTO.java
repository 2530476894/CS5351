package com.se.scrumflow.dto.resp;

import com.se.scrumflow.dto.biz.ActualData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BurndownActualDataRespDTO {

    private List<ActualData> actualRemainingStoryPoints;

}
