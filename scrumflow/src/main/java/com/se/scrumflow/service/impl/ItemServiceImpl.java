package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.repository.ItemRepository;
import com.se.scrumflow.dto.req.ItemCreateReqDTO;
import com.se.scrumflow.dto.req.ItemPageReqDTO;
import com.se.scrumflow.dto.resp.ItemPageRespDTO;
import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import com.se.scrumflow.service.ItemService;
import com.se.scrumflow.utils.Page;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public void createItem(ItemCreateReqDTO requestParam) {
        ItemDO itemDO = BeanUtil.copyProperties(requestParam, ItemDO.class);
        itemRepository.insert(itemDO);
    }

    @Override
    public ItemQueryRespDTO queryItem(ObjectId id) {
        Optional<ItemDO> optionalItemDO = itemRepository.findById(id);
        return optionalItemDO.map(itemDO -> BeanUtil.copyProperties(itemDO, ItemQueryRespDTO.class)).orElse(null);
    }

    @Override
    public Page<ItemPageRespDTO> pageItem(ItemPageReqDTO requestParam) {
        Query query = Query.query(Criteria.where("sprintId").is(requestParam.getSprintId()));
        if (requestParam.getType() != null) {
            query.addCriteria(Criteria.where("type").is(requestParam.getType()));
        }
        if (requestParam.getStatus() != null) {
            query.addCriteria(Criteria.where("status").is(requestParam.getStatus()));
        }
        if (requestParam.getAssignee() != null) {
            query.addCriteria(Criteria.where("assignee").is(requestParam.getAssignee()));
        }
        if (requestParam.getPriority() != null) {
            query.addCriteria(Criteria.where("priority").is(requestParam.getPriority()));
        }
        if (requestParam.getStoryPoint() != null) {
            query.addCriteria(Criteria.where("storyPoint").is(requestParam.getStoryPoint()));
        }
        if (requestParam.getTag() != null) {
            query.addCriteria(Criteria.where("tag").is(requestParam.getTag()));
        }
        if (requestParam.getStartTime() != null) {
            query.addCriteria(Criteria.where("startTime").is(requestParam.getStartTime()));
        }
        if (requestParam.getEndTime() != null) {
            query.addCriteria(Criteria.where("endTime").is(requestParam.getEndTime()));
        }
        query.with(PageRequest.of(requestParam.getPageNumber(), requestParam.getPageSize()));
        List<ItemDO> itemDOList = mongoTemplate.find(query, ItemDO.class);
        List<ItemPageRespDTO> itemPageRespDTOList = itemDOList.stream().map(
                itemDO -> BeanUtil.copyProperties(itemDO, ItemPageRespDTO.class)
        ).toList();
        return Page.create(itemPageRespDTOList, itemPageRespDTOList.size());
    }
}
