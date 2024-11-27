package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.SprintRepository;
import com.se.scrumflow.dto.req.SprintCreateReqDTO;
import com.se.scrumflow.dto.req.SprintUpdateReqDTO;
import com.se.scrumflow.dto.req.SprintWithItemsDTO;
import com.se.scrumflow.dto.resp.AllSprintDTO;
import com.se.scrumflow.dto.resp.SprintQueryDTO;
import com.se.scrumflow.service.SprintService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintRepository sprintRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Result<Void> create(SprintCreateReqDTO requestParam) {
        try {
            SprintDO sprint = BeanUtil.copyProperties(requestParam, SprintDO.class);
            sprintRepository.save(sprint);
            return Results.success();
        } catch (Exception e) {
            return Results.failure("创建Sprint失败", e.getMessage());
        }
    }

    @Override
    public AllSprintDTO getAllSprints() {
        List<SprintDO> sprintDOList = sprintRepository.findAll();
        List<SprintQueryDTO> sprintQueryDTOList = sprintDOList.stream()
                .map(sprintDO -> BeanUtil.copyProperties(sprintDO, SprintQueryDTO.class))
                .toList();
        AllSprintDTO sprints = AllSprintDTO.builder()
                .sprints(sprintQueryDTOList)
                .build();
        return sprints;
    }

    @Override
    public Result<Void> delete(ObjectId sprintId) {
        // 构造查询条件
        Query query = Query.query(Criteria.where("_id").is(sprintId));
        // 删除 Sprint
        DeleteResult deleteResult = mongoTemplate.remove(query, SprintDO.class);
        if (deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() == 0) {
            return Results.failure();
        }
        return Results.success(); // 删除成功，返回成功结果
    }

    @Override
    public Result<Void> update(SprintUpdateReqDTO requestParam) {
        // 构造更新查询条件
        Query query = Query.query(Criteria.where("_id").is(requestParam.getSprintId()));
        // 创建更新操作
        Update update = new Update();
        // 仅更新非空字段
        if (requestParam.getSprintName() != null) {
            update.set("sprintName", requestParam.getSprintName());
        }
        if (requestParam.getStartDate() != null) {
            update.set("startDate", requestParam.getStartDate());
        }
        if (requestParam.getEndDate() != null) {
            update.set("endDate", requestParam.getEndDate());
        }
        if (requestParam.getStatus() != null) {
            update.set("status", requestParam.getStatus());
        }
        if (requestParam.getTotalStoryPoints() >= 0) {
            Query queryItems = new Query(Criteria
                    .where("sprintId").is(requestParam.getSprintId()));
            int total = mongoTemplate.find(queryItems, ItemDO.class).stream().mapToInt(ItemDO::getStoryPoint).sum();
            update.set("totalStoryPoints", total);
        }
        if (requestParam.getCompletedStoryPoints() >= 0) { // 假设允许更新为0
            Query queryItems = new Query(Criteria
                    .where("sprintId").is(requestParam.getSprintId())
                    .and("status").is(2));
            int total = mongoTemplate.find(queryItems, ItemDO.class).stream().mapToInt(ItemDO::getStoryPoint).sum();
            update.set("completedStoryPoints", total);
        }

        // 使用 MongoTemplate 执行更新操作
        UpdateResult result = mongoTemplate.updateFirst(query, update, SprintDO.class);
        // 检查更新结果
        if (result.getMatchedCount() == 0) {
            return Results.failure();
        }
        return Results.success();
    }

    @Override
    public Result<SprintWithItemsDTO> getSprintWithItems(ObjectId sprintId) {
        // 查找 Sprint
        Optional<SprintDO> sprintOptional = sprintRepository.findById(sprintId);
        SprintDO sprint = sprintOptional.get();
        // 使用 MongoTemplate 查询与当前 sprintId 相关的 Items
        Query query = new Query(Criteria.where("sprintId").is(sprintId));
        List<ItemDO> itemList = mongoTemplate.find(query, ItemDO.class);
        // 创建 SprintWithItemsDTO
        SprintWithItemsDTO dto = new SprintWithItemsDTO(sprint, itemList);
        return Results.success(dto);
    }
}
