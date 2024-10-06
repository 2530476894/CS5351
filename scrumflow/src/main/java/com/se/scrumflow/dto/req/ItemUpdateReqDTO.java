package com.se.scrumflow.dto.req;

import com.se.scrumflow.dao.entity.ItemDO;
import org.bson.types.ObjectId;

public class ItemUpdateReqDTO {
    private ObjectId itemId;
    private ItemDO updatedItem;

    public ObjectId getItemId() {
        return itemId;
    }

    public void setItemId(ObjectId itemId) {
        this.itemId = itemId;
    }

    public ItemDO getUpdatedItem() {
        return updatedItem;
    }

    public void setUpdatedItem(ItemDO updatedItem) {
        this.updatedItem = updatedItem;
    }
}
