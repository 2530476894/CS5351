package com.se.scrumflow.service;

import com.se.scrumflow.entity.Project;
import com.se.scrumflow.entity.Sprint;

public interface ProjectService {
    Project createProject(Project project);
    Project addSprintToProject(Sprint sprint, Project project);
}
