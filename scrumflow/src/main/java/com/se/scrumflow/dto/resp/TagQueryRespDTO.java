package com.se.scrumflow.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagQueryRespDTO {

    List<TagDTO> tags;

    Integer total;

}
