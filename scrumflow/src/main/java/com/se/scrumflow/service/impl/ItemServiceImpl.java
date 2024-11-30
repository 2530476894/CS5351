package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.repository.ItemRepository;
import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.dto.req.ItemPageReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateFieldReqDTO;
import com.se.scrumflow.dto.req.ItemUpdateReqDTO;
import com.se.scrumflow.dto.resp.ItemPageRespDTO;
import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import com.se.scrumflow.service.ItemService;
import com.se.scrumflow.utils.GeneralOperations;
import com.se.scrumflow.utils.Page;
import com.se.scrumflow.utils.Time;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.se.scrumflow.common.enums.ItemStatusEnum.UNDO;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public void createItem(ItemCreateReqDTO requestParam) {
        ItemDO itemDO = BeanUtil.copyProperties(requestParam, ItemDO.class);
        itemDO.setStatus(UNDO.getCode());
        itemDO.setDelFlag(0);
        itemDO.setProcess(0);
        itemRepository.insert(itemDO);
    }

    @Override
    public ItemQueryRespDTO queryItem(ObjectId id) {
        Optional<ItemDO> optionalItemDO = itemRepository.findById(id);
        return optionalItemDO
                .filter(itemDO -> itemDO.getDelFlag() != 1)
                .map(itemDO -> BeanUtil.copyProperties(itemDO, ItemQueryRespDTO.class))
                .orElse(null);
    }

    @Override
    public Page<ItemPageRespDTO> pageItem(ItemPageReqDTO requestParam) {
        Query query = GeneralOperations.buildQueryOrUpdate(requestParam, Query.class);
        query.addCriteria(Criteria.where("delFlag").ne(1));
        query.with(PageRequest.of(requestParam.getPageNumber(), requestParam.getPageSize()));
        List<ItemDO> itemDOList = mongoTemplate.find(query, ItemDO.class);
        List<ItemPageRespDTO> itemPageRespDTOList = itemDOList.stream()
                .map(itemDO -> BeanUtil.copyProperties(itemDO, ItemPageRespDTO.class))
                .toList();
        return Page.create(itemPageRespDTOList, itemPageRespDTOList.size());
    }

    @Override
    public void updateItem(ItemUpdateReqDTO requestParam) {
        Query query = new Query(Criteria.where("id").is(requestParam.getId()));
        Update update = GeneralOperations.buildQueryOrUpdate(requestParam, Update.class);
        Time.setUpdateTime(update);
        mongoTemplate.updateFirst(query, update, ItemDO.class);
    }

    @Override
    public void updateItemField(ObjectId id, ItemUpdateFieldReqDTO fields) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = GeneralOperations.buildQueryOrUpdate(fields, Update.class);
        if (fields.getStatus() != null) {
            Time.setDoneTime(update, fields.getStatus());
        }
        Time.setUpdateTime(update);
        mongoTemplate.updateFirst(query, update, ItemDO.class);
    }

    @Override
    public void logicDeleteItem(ObjectId id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = Update.update("delFlag", 1);
        Time.setUpdateTime(update);
        mongoTemplate.updateFirst(query, update, ItemDO.class);
    }
}
