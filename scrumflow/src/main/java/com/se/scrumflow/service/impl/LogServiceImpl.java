package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.se.scrumflow.dao.entity.LogDO;
import com.se.scrumflow.dao.repository.LogRepository;
import com.se.scrumflow.dto.req.LogCreateReqDTO;
import com.se.scrumflow.dto.req.LogPageReqDTO;
import com.se.scrumflow.dto.resp.LogPageRespDTO;
import com.se.scrumflow.service.LogService;
import com.se.scrumflow.utils.GeneralOperations;
import com.se.scrumflow.utils.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public void createLog(LogCreateReqDTO requestParam) {
        LogDO logDO = BeanUtil.copyProperties(requestParam, LogDO.class);
        logRepository.insert(logDO);
    }

    @Override
    public Page<LogPageRespDTO> pageLog(LogPageReqDTO requestParam) {
        Query query = GeneralOperations.buildQueryOrUpdate(requestParam, Query.class);
        query.with(PageRequest.of(requestParam.getPageNumber(), requestParam.getPageSize()));
        List<LogDO> logDOList = mongoTemplate.find(query, LogDO.class);
        List<LogPageRespDTO> logPageRespDTOList = logDOList.stream()
                .map(logDO -> BeanUtil.copyProperties(logDO, LogPageRespDTO.class))
                .toList();
        return Page.create(logPageRespDTOList, logPageRespDTOList.size());
    }

}
