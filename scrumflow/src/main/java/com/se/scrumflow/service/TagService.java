package com.se.scrumflow.service;

import com.se.scrumflow.dto.req.TagCreateReqDTO;
import com.se.scrumflow.dto.resp.TagQueryRespDTO;

public interface TagService {

    void createTag(TagCreateReqDTO requestParam);

    TagQueryRespDTO getAllTag();

    TagQueryRespDTO searchTag(String keyword);

}
