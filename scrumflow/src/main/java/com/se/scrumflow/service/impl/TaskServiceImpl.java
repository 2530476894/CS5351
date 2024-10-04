package com.example.scrumtool.service.impl;

import com.se.scrumflow.entity.BurndownChart;
import com.se.scrumflow.entity.Task;
import com.se.scrumflow.entity.UserStory;
import com.se.scrumflow.repository.BurndownChartRepository;
import com.se.scrumflow.repository.TaskRepository;
import com.se.scrumflow.repository.UserStoryRepository;
import com.se.scrumflow.service.TaskService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final BurndownChartRepository burndownChartRepository;
    private final UserStoryRepository userStoryRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, BurndownChartRepository burndownChartRepository, UserStoryRepository userStoryRepository) {
        this.taskRepository = taskRepository;
        this.burndownChartRepository = burndownChartRepository;
        this.userStoryRepository = userStoryRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskStatus(Task task, boolean newStatus) {
        Task existingTask = taskRepository.findById(task.getId()).orElse(null);
        if (existingTask!= null) {
            existingTask.setCompleted(newStatus);
            Task updatedTask = taskRepository.save(existingTask);

            if (newStatus) {
                ObjectId userStoryId = existingTask.getUserStoryId();
                UserStory userStory = userStoryRepository.findById(userStoryId).orElse(null);
                if (userStory!= null) {
                    ObjectId sprintId = new ObjectId(userStory.getSprint());
                    BurndownChart burndownChart = burndownChartRepository.findBySprintId(sprintId);
                    if (burndownChart!= null) {
                        burndownChart.setCompletedStoryPoints(burndownChart.getCompletedStoryPoints() + existingTask.getStoryPointsForBurndown());
                        burndownChartRepository.save(burndownChart);
                    }
                }
            }

            return updatedTask;
        }
        return null;
    }
}
