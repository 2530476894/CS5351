package com.se.scrumflow.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCreateReqDTO {

    String parentId;

    String name;

    Integer type;

    String description;

}
