package com.se.scrumflow.repository;

import com.se.scrumflow.entity.Sprint;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SprintRepository extends MongoRepository<Sprint, ObjectId> {
    Sprint findByTitle(String title);
}
