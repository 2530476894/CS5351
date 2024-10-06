package com.se.scrumflow.dto;

import com.se.scrumflow.dao.entity.ItemDO;
import org.bson.types.ObjectId;

public class ItemInsertReqDTO {
    private ObjectId sprintId;
    private ItemDO item;

    // 生成getter和setter方法
    public ObjectId getSprintId() {
        return sprintId;
    }

    public void setSprintId(ObjectId sprintId) {
        this.sprintId = sprintId;
    }

    public ItemDO getItem() {
        return item;
    }

    public void setItem(ItemDO item) {
        this.item = item;
    }
}
