package com.se.scrumflow.controller;

import com.se.scrumflow.entity.UserStory;
import com.se.scrumflow.service.UserStoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user - story")
public class UserStoryController {
    private final UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    // 创建用户故事的端点
    @PostMapping("/create")
    public ResponseEntity<UserStory> createUserStory(@RequestBody UserStory userStory) {
        UserStory createdUserStory = userStoryService.createUserStory(userStory);
        return new ResponseEntity<>(createdUserStory, HttpStatus.CREATED);
    }

    // 更新用户故事状态的端点
    @PutMapping("/{id}/status")
    public ResponseEntity<UserStory> updateUserStoryStatus(@PathVariable ObjectId id, @RequestParam String newStatus) {
        UserStory userStory = new UserStory();
        userStory.setId(id);
        UserStory updatedUserStory = userStoryService.updateUserStoryStatus(userStory, newStatus);
        if (updatedUserStory!= null) {
            return new ResponseEntity<>(updatedUserStory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 根据ID获取用户故事的端点（可选项，根据需求添加）
    @GetMapping("/{id}")
    public ResponseEntity<UserStory> getUserStoryById(@PathVariable ObjectId id) {
        // 这里假设UserStoryService有根据ID获取用户故事的方法，需要补充实现
        UserStory userStory = null;
        return new ResponseEntity<>(userStory, HttpStatus.OK);
    }
}

