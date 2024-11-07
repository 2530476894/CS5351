package com.se.scrumflow.service;

import com.se.scrumflow.dto.req.LogCreateReqDTO;
import com.se.scrumflow.dto.req.LogPageReqDTO;
import com.se.scrumflow.dto.resp.LogPageRespDTO;
import com.se.scrumflow.utils.Page;

public interface LogService {

    void createLog(LogCreateReqDTO requestParam);

    Page<LogPageRespDTO> pageLog(LogPageReqDTO requestParam);

}
