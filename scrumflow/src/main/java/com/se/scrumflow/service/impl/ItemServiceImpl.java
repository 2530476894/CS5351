package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.repository.ItemRepository;
import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public void createItem(ItemCreateReqDTO requestParam) {
        ItemDO itemDO = BeanUtil.copyProperties(requestParam, ItemDO.class);
        itemRepository.insert(itemDO);
    }

}
