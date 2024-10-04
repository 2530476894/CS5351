package com.se.scrumflow.repository;


import com.se.scrumflow.entity.Backlog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BacklogRepository extends MongoRepository<Backlog, ObjectId> {
    Backlog findByTitle(String backlogTitle);
}

