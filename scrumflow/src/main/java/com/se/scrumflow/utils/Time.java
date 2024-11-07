package com.se.scrumflow.utils;

import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

public class Time {

    public static void setUpdateTime(Update update) {
        update.set("updateTime", new Date());
    }
}
