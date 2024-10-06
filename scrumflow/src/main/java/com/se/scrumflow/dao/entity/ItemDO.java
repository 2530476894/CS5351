package com.se.scrumflow.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

import static com.se.scrumflow.common.constant.ItemConstants.MAX_SUB_ITEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "col_item")
public class ItemDO {

    @MongoId
    @Field("_id")
    ObjectId id;

    String parentId;

    String name;

    /**
     * 区分事项类型
     */
    Integer type;

    List<ItemDO> subItems = new ArrayList<>(MAX_SUB_ITEM);

    String description;

    List<LogDO> logs = new ArrayList<>();

}
