package com.se.scrumflow.service;

import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.dto.req.ItemPageReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateFieldReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateReqDTO;
import com.se.scrumflow.dto.resp.ItemPageRespDTO;
import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import com.se.scrumflow.utils.Page;
import org.bson.types.ObjectId;

import java.util.List;

public interface ItemService {

    void createItem(ItemCreateReqDTO requestParam);

    ItemQueryRespDTO queryItem(ObjectId id);

    Page<ItemPageRespDTO> pageItem(ItemPageReqDTO requestParam);

    void updateItem(ItemUpdateReqDTO requestParam);

    void updateItemField(ObjectId id, ItemUpdateFieldReqDTO fields);

    void logicDeleteItem(ObjectId id);

    List<ItemQueryRespDTO> getAllItem();

}
