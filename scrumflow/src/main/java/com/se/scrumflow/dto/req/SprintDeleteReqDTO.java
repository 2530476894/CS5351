package com.se.scrumflow.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintDeleteReqDTO {
    private String sprintId; // Sprint 的 ID
}

