package com.se.scrumflow.service;

import com.se.scrumflow.entity.UserStory;

public interface UserStoryService {
    UserStory createUserStory(UserStory userStory);
    UserStory updateUserStoryStatus(UserStory userStory, String newStatus);
}
