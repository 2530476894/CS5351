package com.se.scrumflow.service;

import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.dto.req.ItemPageReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateReqDTO;
import com.se.scrumflow.dto.resp.ItemPageRespDTO;
import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import com.se.scrumflow.utils.Page;
import org.bson.types.ObjectId;

public interface ItemService {

    void createItem(ItemCreateReqDTO requestParam);

    ItemQueryRespDTO queryItem(ObjectId id);

    Page<ItemPageRespDTO> pageItem(ItemPageReqDTO requestParam);

    void updateItem(ItemUpdateReqDTO requestParam);
}
