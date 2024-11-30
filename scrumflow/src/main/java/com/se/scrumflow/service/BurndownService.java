package com.se.scrumflow.service;

import com.se.scrumflow.dto.resp.BurndownActualDataRespDTO;
import com.se.scrumflow.dto.resp.BurndownPlanDataRespDTO;
import org.bson.types.ObjectId;

public interface BurndownService {

    BurndownPlanDataRespDTO generateBurndownPlanData(ObjectId id);

    BurndownActualDataRespDTO generateBurndownActualData(ObjectId id);

}