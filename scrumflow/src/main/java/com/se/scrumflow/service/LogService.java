package com.se.scrumflow.service;

import com.se.scrumflow.dto.req.LogCreateReqDTO;

public interface LogService {

    void createLog(LogCreateReqDTO requestParam);

}
