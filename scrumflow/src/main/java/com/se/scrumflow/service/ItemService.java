package com.se.scrumflow.service;

import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.dto.req.ItemPageReqDTO;
import com.se.scrumflow.dto.resp.ItemPageRespDTO;
import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import com.se.scrumflow.utils.Page;

public interface ItemService {

    void createItem(ItemCreateReqDTO requestParam);

    ItemQueryRespDTO queryItem(String id);

    Page<ItemPageRespDTO> pageItem(ItemPageReqDTO requestParam);
}
