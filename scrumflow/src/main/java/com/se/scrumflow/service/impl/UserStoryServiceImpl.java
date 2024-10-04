package com.se.scrumflow.service.impl;

import com.se.scrumflow.entity.BurndownChart;
import com.se.scrumflow.entity.UserStory;
import com.se.scrumflow.repository.BurndownChartRepository;
import com.se.scrumflow.repository.UserStoryRepository;
import com.se.scrumflow.service.UserStoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStoryServiceImpl implements UserStoryService {
    private final UserStoryRepository userStoryRepository;
    private final BurndownChartRepository burndownChartRepository;

    @Autowired
    public UserStoryServiceImpl(UserStoryRepository userStoryRepository, BurndownChartRepository burndownChartRepository) {
        this.userStoryRepository = userStoryRepository;
        this.burndownChartRepository = burndownChartRepository;
    }

    @Override
    public UserStory createUserStory(UserStory userStory) {
        return userStoryRepository.save(userStory);
    }

    @Override
    public UserStory updateUserStoryStatus(UserStory userStory, String newStatus) {
        UserStory existingUserStory = userStoryRepository.findById(userStory.getId()).orElse(null);
        if (existingUserStory!= null) {
            existingUserStory.setStatus(newStatus);
            UserStory updatedUserStory = userStoryRepository.save(existingUserStory);

            if ("已完成".equals(newStatus)) {
                ObjectId sprintId = new ObjectId(updatedUserStory.getSprint());
                BurndownChart burndownChart = burndownChartRepository.findBySprintId(sprintId);
                if (burndownChart!= null) {
                    burndownChart.setCompletedStoryPoints(burndownChart.getCompletedStoryPoints() + updatedUserStory.getStoryPointsForBurndown());
                    burndownChartRepository.save(burndownChart);
                }
            }

            return updatedUserStory;
        }
        return null;
    }
}
