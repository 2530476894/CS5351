package com.se.scrumflow.service;

import com.se.scrumflow.entity.Backlog;
import org.bson.types.ObjectId;


import java.util.List;

public interface BacklogService {
    Backlog createBacklog(Backlog backlog);
    Backlog getBacklogById(ObjectId id);
    Backlog updateBacklog(Backlog backlog);
    void deleteBacklog(ObjectId id);
    List<Backlog> getAllBacklogs();
}

