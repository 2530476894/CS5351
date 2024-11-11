package com.se.scrumflow.dao.repository;

import com.se.scrumflow.dao.entity.ItemDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<ItemDO, ObjectId> {
    Optional<ItemDO> findById(ObjectId id);
}
