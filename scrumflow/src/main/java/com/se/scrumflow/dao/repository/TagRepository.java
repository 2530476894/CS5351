package com.se.scrumflow.dao.repository;

import com.se.scrumflow.dao.entity.TagDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<TagDO, ObjectId> {
}