package com.se.scrumflow.service;

import com.se.scrumflow.entity.Task;

public interface TaskService {
    Task createTask(Task task);
    Task updateTaskStatus(Task task, boolean newStatus);
}
