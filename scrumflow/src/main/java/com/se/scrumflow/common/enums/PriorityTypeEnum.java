package com.se.scrumflow.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PriorityTypeEnum {

    LOW(0, "低"),

    MIDDLE(1, "中"),

    HIGH(2, "高"),

    VITAL(3, "紧急");

    @Getter
    private final int code;

    @Getter
    private final String name;

}
