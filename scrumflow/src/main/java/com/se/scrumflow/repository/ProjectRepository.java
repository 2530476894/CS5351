package com.se.scrumflow.repository;

import com.se.scrumflow.entity.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
}
