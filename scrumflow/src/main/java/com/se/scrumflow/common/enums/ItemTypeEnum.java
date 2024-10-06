package com.se.scrumflow.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemTypeEnum {

    USER_STORY(0, "用户故事"),

    TASK(1, "任务"),

    BUG(2, "漏洞"),

    SUBITEM(3, "需求");

    @Getter
    private final int code;

    @Getter
    private final String name;

}
