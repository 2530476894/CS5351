package com.se.scrumflow.service;

import com.se.scrumflow.dto.req.TagCreateReqDTO;
import com.se.scrumflow.dto.resp.TagQueryRespDTO;
import org.bson.types.ObjectId;

public interface TagService {

    void createTag(TagCreateReqDTO requestParam);

    TagQueryRespDTO getAllTag();

    TagQueryRespDTO searchTag(String keyword);

    void logicDeleteTag(ObjectId id);

}
