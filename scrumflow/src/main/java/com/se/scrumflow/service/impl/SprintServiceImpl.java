package com.se.scrumflow.service.impl;
import com.se.scrumflow.common.convention.errorcode.BaseErrorCode;
import com.se.scrumflow.common.convention.result.Result;
import com.se.scrumflow.common.convention.result.Results;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.ItemRepository;
import com.se.scrumflow.dao.repository.SprintRepository;

import com.se.scrumflow.dto.ItemInsertReqDTO;
import com.se.scrumflow.dto.req.*;
import com.se.scrumflow.service.SprintService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Result<Void> create(SprintCreateReqDTO requestParam) {
        try {
            SprintDO sprint = new SprintDO();
            sprint.setSprintName(requestParam.getSprintName());
            sprint.setStartDate(requestParam.getStartDate());
            sprint.setEndDate(requestParam.getEndDate());
            sprint.setStatus(requestParam.getStatus());
            sprint.setTotalStoryPoints(requestParam.getTotalStoryPoints());
            sprint.setCompletedStoryPoints(requestParam.getCompletedStoryPoints());
            sprintRepository.save(sprint);
            return Results.success();
        } catch (Exception e) {
            return Results.failure("创建Sprint失败", e.getMessage());
        }
    }

    @Override
    public Result<Void> delete(SprintDeleteReqDTO requestParam) {
        try {
            sprintRepository.deleteById(requestParam.getSprintId());
            return Results.success();
        } catch (Exception e) {
            return Results.failure("删除Sprint失败", e.getMessage());
        }
    }

    @Override
    public Result<SprintDO> insertItem(ItemInsertReqDTO requestParam) {
        try {
            SprintDO sprint = sprintRepository.findById(requestParam.getSprintId()).orElseThrow(() -> new RuntimeException("未找到指定的Sprint"));
            if (sprint.getItems() == null) {
                sprint.setItems(new ArrayList<>());
            }
            // 先保存Item为独立文档（如果还没有保存）
            ItemDO item = requestParam.getItem();
            if (item.getId() == null) {
                item = itemRepository.save(item);
            }
            sprint.getItems().add(item.getId());
            SprintDO savedSprint = sprintRepository.save(sprint);
            return Results.success(savedSprint);
        } catch (Exception e) {
            return new Result<SprintDO>()
                    .setCode("更新Item失败错误码，可自定义")
                    .setMessage("更新Item失败: " + e.getMessage())
                    .setData(null);
        }
    }



    @Override
    public Result<ItemDO> updateItem(ItemUpdateReqDTO requestParam) {
        try {
            return itemRepository.findById(requestParam.getItemId())
                    .map(item -> {
                        // 根据具体需求更新Item的各个属性
                        if (requestParam.getUpdatedItem().getName()!= null) {
                            item.setName(requestParam.getUpdatedItem().getName());
                        }
                        if (requestParam.getUpdatedItem().getDescription()!= null) {
                            item.setDescription(requestParam.getUpdatedItem().getDescription());
                        }
                        // 更新其他属性...
                        // 更新 Sprint 中的 Item id
//                        for (SprintDO sprint : sprintRepository.findAll()) {
//                            if (sprint.getItems()!= null) {
//                                List<ObjectId> newItems = new ArrayList<>();
//                                for (ObjectId itemId : sprint.getItems()) {
//                                    if (itemId.equals(requestParam.getItemId())) {
//                                        newItems.add(item.getId());
//                                    } else {
//                                        newItems.add(itemId);
//                                    }
//                                }
//                                sprint.setItems(newItems);
//                                sprintRepository.save(sprint);
//                            }
//                        }
                        return Results.success(itemRepository.save(item));
                    })
                    .orElse(new Result<ItemDO>()
                            .setCode("未找到指定的Item错误码，可自定义")
                            .setMessage("未找到指定的Item")
                            .setData(null));
        } catch (Exception e) {
            return new Result<ItemDO>()
                    .setCode("更新Item失败错误码，可自定义")
                    .setMessage("更新Item失败: " + e.getMessage())
                    .setData(null);
        }
    }

    @Override
    public Result<SprintWithItemsDTO> getSprintWithItems(ObjectId sprintId) {
        try {
            Optional<SprintDO> sprintOptional = sprintRepository.findById(sprintId);
            if (sprintOptional.isEmpty()) {
                Result<SprintWithItemsDTO> result = new Result<>();
                result.setCode(BaseErrorCode.SPRINT_NOT_FOUND.code());
                result.setMessage(BaseErrorCode.SPRINT_NOT_FOUND.message());
                return result;
            }
            SprintDO sprint = sprintOptional.get();
            List<ItemDO> itemList = new ArrayList<>();
            if (sprint.getItems()!= null) {
                for (ObjectId itemId : sprint.getItems()) {
                    Optional<ItemDO> itemOptional = itemRepository.findById(itemId);
                    if (itemOptional.isEmpty()) {
                        Result<SprintWithItemsDTO> result = new Result<>();
                        result.setCode(BaseErrorCode.ITEM_NOT_FOUND_IN_SPRINT.code());
                        result.setMessage(BaseErrorCode.ITEM_NOT_FOUND_IN_SPRINT.message());
                        return result;
                    }
                    itemList.add(itemOptional.get());
                }
            }
            SprintWithItemsDTO dto = new SprintWithItemsDTO(sprint, itemList);
            return Results.success(dto);
        } catch (Exception e) {
            Result<SprintWithItemsDTO> result = new Result<>();
            result.setCode(BaseErrorCode.GET_SPRINT_WITH_ITEMS_FAILED.code());
            result.setMessage(BaseErrorCode.GET_SPRINT_WITH_ITEMS_FAILED.message());
            return result;
        }
    }



}
