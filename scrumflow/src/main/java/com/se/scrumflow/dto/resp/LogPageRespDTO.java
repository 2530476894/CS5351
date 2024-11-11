package com.se.scrumflow.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogPageRespDTO {

    String id;

    String itemId;

    String content;

    Date createTime;

}
