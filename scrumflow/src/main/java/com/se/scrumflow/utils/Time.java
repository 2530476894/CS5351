package com.se.scrumflow.utils;

import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

import static com.se.scrumflow.common.enums.ItemStatusEnum.DONE;

public class Time {

    public static void setUpdateTime(Update update) {
        update.set("updateTime", new Date());
    }

    public static void setDoneTime(Update update, Integer status) {
        if (status == DONE.getCode()) {
            update.set("doneTime", new Date());
        } else {
            update.set("doneTime", null);
        }
    }
}
