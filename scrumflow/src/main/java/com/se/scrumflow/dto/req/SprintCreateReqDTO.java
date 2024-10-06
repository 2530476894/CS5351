package com.se.scrumflow.dto.req;

import java.util.Date;

public class SprintCreateReqDTO {
    private String sprintName;
    private Date startDate;
    private Date endDate;
    private String status;
    private int totalStoryPoints;
    private int completedStoryPoints;

    // 生成getter和setter方法
    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalStoryPoints() {
        return totalStoryPoints;
    }

    public void setTotalStoryPoints(int totalStoryPoints) {
        this.totalStoryPoints = totalStoryPoints;
    }

    public int getCompletedStoryPoints() {
        return completedStoryPoints;
    }

    public void setCompletedStoryPoints(int completedStoryPoints) {
        this.completedStoryPoints = completedStoryPoints;
    }
}

