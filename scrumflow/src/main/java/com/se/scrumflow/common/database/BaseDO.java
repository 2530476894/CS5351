package com.se.scrumflow.common.database;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    private Integer delFlag;

}
