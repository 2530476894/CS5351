package com.se.scrumflow.service.impl;

import com.se.scrumflow.entity.Backlog;
import com.se.scrumflow.repository.BacklogRepository;
import com.se.scrumflow.service.BacklogService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BacklogServiceImpl implements BacklogService {
    private final BacklogRepository backlogRepository;

    @Autowired
    public BacklogServiceImpl(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    @Override
    public Backlog createBacklog(Backlog backlog) {
        return backlogRepository.save(backlog);
    }

    @Override
    public Backlog getBacklogById(ObjectId id) {
        return backlogRepository.findById(id).orElse(null);
    }

    @Override
    public Backlog updateBacklog(Backlog backlog) {
        return backlogRepository.save(backlog);
    }

    @Override
    public void deleteBacklog(ObjectId id) {
        backlogRepository.deleteById(id);
    }

    @Override
    public List<Backlog> getAllBacklogs() {
        return backlogRepository.findAll();
    }
}
