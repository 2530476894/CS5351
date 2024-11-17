package com.se.scrumflow.dao.repository;

import com.se.scrumflow.dao.entity.BurndownDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface BurndownRepository extends MongoRepository<BurndownDO, ObjectId> {
    BurndownDO findBySprintId(ObjectId sprintId);
}