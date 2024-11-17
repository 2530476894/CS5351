package com.se.scrumflow.service;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dto.req.SprintCreateReqDTO;
import com.se.scrumflow.dto.req.SprintUpdateReqDTO;
import com.se.scrumflow.dto.req.SprintWithItemsDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface SprintService {
    Result<Void> create(SprintCreateReqDTO requestParam);
    Result<List<SprintDO>> getAllSprints();
    Result<Void> delete(ObjectId sprintId);
    Result<Void> update(SprintUpdateReqDTO requestParam);
    Result<SprintWithItemsDTO> getSprintWithItems(ObjectId sprintId);
}

