package com.se.scrumflow.dao.entity;

import com.se.scrumflow.common.database.BaseDO;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "col_sprint")
@Data
public class SprintDO extends BaseDO {

    @Id
    private ObjectId id;

    private String title;

    private String description;

    private Date startDate;

    private Date endDate;

}