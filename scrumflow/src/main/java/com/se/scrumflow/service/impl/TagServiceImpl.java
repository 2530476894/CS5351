package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.se.scrumflow.dao.entity.TagDO;
import com.se.scrumflow.dao.repository.TagRepository;
import com.se.scrumflow.dto.req.TagCreateReqDTO;
import com.se.scrumflow.dto.resp.TagDTO;
import com.se.scrumflow.dto.resp.TagQueryRespDTO;
import com.se.scrumflow.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public void createTag(TagCreateReqDTO requestParam) {
        TagDO tagDO = BeanUtil.copyProperties(requestParam, TagDO.class);
        tagDO.setDelFlag(0);
        tagRepository.insert(tagDO);
    }

    @Override
    public TagQueryRespDTO getAllTag() {
        List<TagDO> tagDOList = tagRepository.findAll();
        List<TagDTO> tagDTOS = tagDOList.stream()
                .filter(tagDO -> tagDO.getDelFlag() != 1)
                .map(tagDO -> BeanUtil.copyProperties(tagDO, TagDTO.class))
                .toList();
        return TagQueryRespDTO.builder()
                .tags(tagDTOS)
                .total(tagDTOS.size())
                .build();
    }

    @Override
    public TagQueryRespDTO searchTag(String keyword) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(keyword, "i"));
        List<TagDO> tagDOList = mongoTemplate.find(query, TagDO.class);
        List<TagDTO> tagDTOS = tagDOList.stream()
                .filter(tagDO -> tagDO.getDelFlag() != 1)
                .map(tagDO -> BeanUtil.copyProperties(tagDO, TagDTO.class))
                .toList();
        return TagQueryRespDTO.builder()
                .tags(tagDTOS)
                .total(tagDTOS.size())
                .build();
    }
}
