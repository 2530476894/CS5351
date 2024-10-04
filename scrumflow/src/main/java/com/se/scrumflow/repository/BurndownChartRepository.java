package com.se.scrumflow.repository;

import com.se.scrumflow.entity.BurndownChart;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BurndownChartRepository extends MongoRepository<BurndownChart, ObjectId> {
    BurndownChart findBySprintId(ObjectId sprintId);
}
