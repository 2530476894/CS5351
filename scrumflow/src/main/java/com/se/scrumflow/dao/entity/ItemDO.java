package com.se.scrumflow.dao.entity;

import com.se.scrumflow.common.database.BaseDO;
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
public class ItemDO extends BaseDO {

    @MongoId
    @Field("_id")
    private ObjectId id;

    private String parentId;

    private String name;

    /**
     * 区分事项类型
     */
    private Integer type; // Consider using an enum for better type safety

    private List<ItemDO> subItems = new ArrayList<>();

    private String description;

    private List<LogDO> logs = new ArrayList<>();

    // New fields for Burndown integration
    private Integer storyPoints; // Story points for estimating effort
    private Integer estimatedTime; // Estimated time to complete (in hours or minutes)
    private String status; // Current status (e.g., "To Do", "In Progress", "Done")
}
