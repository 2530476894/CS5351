package com.se.scrumflow.repository;

import com.se.scrumflow.entity.SubTask;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubTaskRepository extends MongoRepository<SubTask, ObjectId> {
}
