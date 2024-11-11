package com.se.scrumflow.dto.req;

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
public class SprintUpdateReqDTO {
    private ObjectId sprintId; // Sprint 的 ID
    private String sprintName; // Sprint 名称（可选）
    private Date startDate; // 开始日期（可选）
    private Date endDate; // 结束日期（可选）
    private Integer status; // 状态（可选）
    private int totalStoryPoints; // 总故事点（可选）
    private int completedStoryPoints; // 已完成的故事点（可选）
}