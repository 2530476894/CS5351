package com.se.scrumflow.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page<T> {

    List<T> records;

    Integer total;

    public static <T> Page<T> create(List<T> records, Integer total) {
        return new Page<>(records, total);
    }

}
