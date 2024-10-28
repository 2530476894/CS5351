package com.se.scrumflow.service.impl;

import com.se.scrumflow.dao.entity.BurndownDO;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;
import com.se.scrumflow.dao.repository.BurndownRepository;
import com.se.scrumflow.dao.repository.SprintRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BurndownServiceImplTest {

    @InjectMocks
    private BurndownServiceImpl burndownService;

    @Mock
    private BurndownRepository burndownRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private SprintRepository sprintRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBurndown_shouldCreateNewBurndown() {
        ObjectId sprintId = new ObjectId();
        SprintDO sprint = new SprintDO();
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 7 * 86400000); // 七天的时间段
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);

        BurndownDO burndownDO = new BurndownDO();
        burndownDO.setTotalStoryPoints(0); // 初始化为0

        // 模拟行为
        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sprint));
        when(burndownRepository.findBySprintId(sprintId)).thenReturn(null);

        List<ItemDO> items = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ItemDO item = new ItemDO();
            item.setStoryPoint(i);
            items.add(item);
        }

        when(mongoTemplate.find(any(Query.class), eq(ItemDO.class))).thenReturn(items);

        BurndownDO result = burndownService.createBurndown(sprintId);

        assertNotNull(result);
        assertEquals(sprintId, result.getSprintId());
        assertEquals(startDate, result.getStartTime());
        assertEquals(endDate, result.getEndTime());
        assertEquals(6, result.getTotalStoryPoints()); // 1 + 2 + 3 = 6

        verify(burndownRepository).save(result);
    }

    @Test
    void createBurndown_shouldReturnExistingBurndown() {
        ObjectId sprintId = new ObjectId();
        BurndownDO existingBurndown = new BurndownDO();

        when(burndownRepository.findBySprintId(sprintId)).thenReturn(existingBurndown);

        BurndownDO result = burndownService.createBurndown(sprintId);

        assertNotNull(result);
        assertEquals(existingBurndown, result);
        verify(burndownRepository, never()).save(any());
    }

    @Test
    void createBurndown_shouldThrowExceptionWhenSprintNotFound() {
        ObjectId sprintId = new ObjectId();
        when(burndownRepository.findBySprintId(sprintId)).thenReturn(null);
        when(sprintRepository.findById(sprintId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            burndownService.createBurndown(sprintId);
        });

        assertEquals("找不到对应的SprintDO，sprintId: " + sprintId, exception.getMessage());
    }

    @Test
    void initBurndown_shouldInitializePlannedRemainingStoryPoints() {
        ObjectId sprintId = new ObjectId();
        SprintDO sprint = new SprintDO();
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 7 * 86400000); // 七天的时间段
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);

        BurndownDO burndownDO = new BurndownDO();
        burndownDO.setTotalStoryPoints(10);

        // 设置模拟行为
        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sprint));
        when(burndownRepository.findBySprintId(sprintId)).thenReturn(burndownDO);

        List<ItemDO> items = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ItemDO item = new ItemDO();
            item.setStoryPoint(1); // 每天计划1点
            item.setEndTime(new Date(startDate.getTime() + i * 86400000)); // 设置计划结束时间
            items.add(item);
        }

        // 模拟查询 ItemDO
        when(mongoTemplate.find(any(Query.class), eq(ItemDO.class))).thenReturn(items);

        BurndownDO result = burndownService.initBurndown(sprintId);

        assertNotNull(result);
        assertEquals(7, result.getPlannedRemainingStoryPoints().size());
        assertEquals(9, result.getPlannedRemainingStoryPoints().get(0).getValue()); // Day 1
        assertEquals(8, result.getPlannedRemainingStoryPoints().get(1).getValue()); // Day 2
        assertEquals(7, result.getPlannedRemainingStoryPoints().get(2).getValue()); // Day 3
        assertEquals(6, result.getPlannedRemainingStoryPoints().get(3).getValue()); // Day 4
        assertEquals(5, result.getPlannedRemainingStoryPoints().get(4).getValue()); // Day 5
        assertEquals(4, result.getPlannedRemainingStoryPoints().get(5).getValue()); // Day 6
        assertEquals(3, result.getPlannedRemainingStoryPoints().get(6).getValue()); // Day 7

        verify(burndownRepository).save(result);
    }

    @Test
    void initBurndown_shouldThrowExceptionWhenBurndownNotFound() {
        ObjectId sprintId = new ObjectId();
        when(burndownRepository.findBySprintId(sprintId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            burndownService.initBurndown(sprintId);
        });

        assertEquals("找不到对应的BurndownDO，sprintId: " + sprintId, exception.getMessage());
    }

    @Test
    void initBurndown_shouldThrowExceptionWhenSprintNotFound() {
        ObjectId sprintId = new ObjectId();
        BurndownDO burndownDO = new BurndownDO();
        burndownDO.setTotalStoryPoints(10);

        when(burndownRepository.findBySprintId(sprintId)).thenReturn(burndownDO);
        when(mongoTemplate.findById(sprintId, SprintDO.class)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            burndownService.initBurndown(sprintId);
        });

        assertEquals("找不到对应的SprintDO，sprintId: " + sprintId, exception.getMessage());
    }

    @Test
    void updateBurndown_shouldUpdateActualRemainingStoryPoints() {
        ObjectId sprintId = new ObjectId();
        SprintDO sprint = new SprintDO();
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 7 * 86400000); // 七天的时间段
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);

        BurndownDO burndownDO = new BurndownDO();
        burndownDO.setTotalStoryPoints(10);

        // 设置模拟行为
        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sprint));
        when(burndownRepository.findBySprintId(sprintId)).thenReturn(burndownDO);

        List<ItemDO> items = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ItemDO item = new ItemDO();
            item.setStoryPoint(1); // 每天完成1点
            item.setStatus(1); // 假设状态为1表示完成
            item.setEndTime(new Date(startDate.getTime() + i * 86400000)); // 设置计划结束时间
            items.add(item);
        }

        // 模拟查询 ItemDO
        when(mongoTemplate.find(any(Query.class), eq(ItemDO.class))).thenReturn(items);

        BurndownDO result = burndownService.updateBurndown(sprintId);

        assertNotNull(result);
        assertEquals(7, result.getActualRemainingStoryPoints().size());
        assertEquals(9, result.getActualRemainingStoryPoints().get(0).getValue()); // Day 1
        assertEquals(8, result.getActualRemainingStoryPoints().get(1).getValue()); // Day 2
        assertEquals(7, result.getActualRemainingStoryPoints().get(2).getValue()); // Day 3
        assertEquals(6, result.getActualRemainingStoryPoints().get(3).getValue()); // Day 4
        assertEquals(5, result.getActualRemainingStoryPoints().get(4).getValue()); // Day 5
        assertEquals(4, result.getActualRemainingStoryPoints().get(5).getValue()); // Day 6
        assertEquals(3, result.getActualRemainingStoryPoints().get(6).getValue()); // Day 7

        verify(burndownRepository).save(result);
    }

    @Test
    void updateBurndown_shouldThrowExceptionWhenBurndownNotFound() {
        ObjectId sprintId = new ObjectId();
        when(burndownRepository.findBySprintId(sprintId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            burndownService.updateBurndown(sprintId);
        });

        assertEquals("找不到对应的BurndownDO，sprintId: " + sprintId, exception.getMessage());
    }

    @Test
    void updateBurndown_shouldThrowExceptionWhenSprintNotFound() {
        ObjectId sprintId = new ObjectId();
        BurndownDO burndownDO = new BurndownDO();
        burndownDO.setTotalStoryPoints(10);

        when(burndownRepository.findBySprintId(sprintId)).thenReturn(burndownDO);
        when(mongoTemplate.findById(sprintId, SprintDO.class)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            burndownService.updateBurndown(sprintId);
        });

        assertEquals("找不到对应的SprintDO，sprintId: " + sprintId, exception.getMessage());
    }
}