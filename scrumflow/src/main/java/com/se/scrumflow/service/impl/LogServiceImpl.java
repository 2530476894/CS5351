package com.se.scrumflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.se.scrumflow.dao.entity.LogDO;
import com.se.scrumflow.dao.repository.LogRepository;
import com.se.scrumflow.dto.req.LogCreateReqDTO;
import com.se.scrumflow.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    public void createLog(LogCreateReqDTO requestParam) {
        LogDO logDO = BeanUtil.copyProperties(requestParam, LogDO.class);
        logRepository.insert(logDO);
    }

}
