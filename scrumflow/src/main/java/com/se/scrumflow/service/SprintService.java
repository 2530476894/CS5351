package com.se.scrumflow.service;

import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dto.ItemInsertReqDTO;
import com.se.scrumflow.dto.req.*;
import org.bson.types.ObjectId;

import java.util.Date;

public interface SprintService {
    Result<Void> create(SprintCreateReqDTO requestParam);
    Result<Void> delete(SprintDeleteReqDTO requestParam);
    Result<SprintDO> insertItem(ItemInsertReqDTO requestParam);
    Result<ItemDO> updateItem(ItemUpdateReqDTO requestParam);
    Result<SprintWithItemsDTO> getSprintWithItems(ObjectId sprintId);
}

