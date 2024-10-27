package com.se.scrumflow.service;

import com.se.scrumflow.dao.entity.BurndownDO;
import org.bson.types.ObjectId;

public interface BurndownService {
    BurndownDO createOrUpdateAndGetBurndownData(ObjectId sprintId);
}