package com.se.scrumflow.dao.repository;


import com.se.scrumflow.dao.entity.SprintDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SprintRepository extends MongoRepository<SprintDO, ObjectId> {
    Optional<SprintDO> findById(ObjectId id);
}
