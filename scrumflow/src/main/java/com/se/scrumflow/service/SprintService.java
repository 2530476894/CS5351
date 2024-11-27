package com.se.scrumflow.service;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.dto.req.SprintCreateReqDTO;
import com.se.scrumflow.dto.req.SprintUpdateReqDTO;
import com.se.scrumflow.dto.req.SprintWithItemsDTO;
import com.se.scrumflow.dto.resp.AllSprintDTO;
import org.bson.types.ObjectId;

public interface SprintService {
    Result<Void> create(SprintCreateReqDTO requestParam);
    AllSprintDTO getAllSprints();
    Result<Void> delete(ObjectId sprintId);
    Result<Void> update(SprintUpdateReqDTO requestParam);
    Result<SprintWithItemsDTO> getSprintWithItems(ObjectId sprintId);
}

