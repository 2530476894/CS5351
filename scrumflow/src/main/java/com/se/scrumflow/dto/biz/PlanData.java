package com.se.scrumflow.dto.biz;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanData {

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date date;

    Integer planPoints;

}
