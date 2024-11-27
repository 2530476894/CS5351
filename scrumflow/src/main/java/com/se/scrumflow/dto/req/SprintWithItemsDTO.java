package com.se.scrumflow.dto.req;

import com.se.scrumflow.dto.resp.ItemQueryRespDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintWithItemsDTO {

    private List<ItemQueryRespDTO> items;

}

