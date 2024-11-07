package com.se.scrumflow.dao.repository;

import com.se.scrumflow.dao.entity.LogDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogDO, ObjectId> {
}
