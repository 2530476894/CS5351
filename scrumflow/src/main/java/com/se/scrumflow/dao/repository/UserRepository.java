package com.se.scrumflow.dao.repository;

import com.se.scrumflow.dao.entity.UserDO;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDO, ObjectId> {
    // @Query(value="{ 'user_id': ?0 }")
    // Optional<UserDO> findByUserID(String userID);

    List<UserDO> findByID(String userID);

    // @Query(value="{ 'user_name': ?0 }")
    // List<UserDO> findByUserName(String userName);

    List<UserDO> findByName(String userName);

}
