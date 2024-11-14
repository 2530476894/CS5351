package com.se.scrumflow.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannedRemainingStoryPointDTO {
    private Date date;
    private int value;
}


