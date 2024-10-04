package com.se.scrumflow.repository;

import com.se.scrumflow.entity.UserStory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStoryRepository extends MongoRepository<UserStory, ObjectId> {
    UserStory findByTitle(String title);
}
