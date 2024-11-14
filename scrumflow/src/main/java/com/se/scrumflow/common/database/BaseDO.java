package com.se.scrumflow.common.database;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class BaseDO {

    /**
     * 创建时间
     */
    @CreatedDate
    @Field("createTime")
    private Date createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Field("updateTime")
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    private Integer delFlag;

}
