package com.se.scrumflow.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatusTypeEnum {

    UNDO(0, "未开始"),

    DOING(1, "进行中"),

    DONE(2, "已完成"),

    TODO(3, "暂缓");

    @Getter
    private final int code;

    @Getter
    private final String name;

}
