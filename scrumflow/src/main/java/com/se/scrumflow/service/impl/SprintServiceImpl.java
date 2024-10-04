package com.se.scrumflow.service.impl;

import com.se.scrumflow.entity.Project;
import com.se.scrumflow.entity.Sprint;
import com.se.scrumflow.entity.UserStory;
import com.se.scrumflow.repository.SprintRepository;
import com.se.scrumflow.service.SprintService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;

    @Autowired
    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override
    public Sprint createSprint(Sprint sprint, Project project) {
        sprint.setProject(project);
        return sprintRepository.save(sprint);
    }

    @Override
    public Sprint addUserStoryToSprint(UserStory userStory, Sprint sprint) {
        if (sprint!= null && userStory!= null) {
            sprint.getUserStories().add(userStory);
            return sprintRepository.save(sprint);
        }
        return null;
    }
}
