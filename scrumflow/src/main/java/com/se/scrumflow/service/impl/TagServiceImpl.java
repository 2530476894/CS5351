package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.se.scrumflow.dao.entity.TagDO;
import com.se.scrumflow.dao.repository.TagRepository;
import com.se.scrumflow.dto.req.TagCreateReqDTO;
import com.se.scrumflow.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public void createTag(TagCreateReqDTO requestParam) {
        TagDO tagDO = BeanUtil.copyProperties(requestParam, TagDO.class);
        tagDO.setDelFlag(0);
        tagRepository.insert(tagDO);
    }

}
