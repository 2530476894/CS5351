package com.se.scrumflow.service;

import com.se.scrumflow.entity.Project;
import com.se.scrumflow.entity.Sprint;
import com.se.scrumflow.entity.UserStory;
import org.bson.types.ObjectId;

public interface SprintService {
    Sprint createSprint(Sprint sprint, Project project);
    Sprint addUserStoryToSprint(UserStory userStory, Sprint sprint);
}
