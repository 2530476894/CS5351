package com.se.scrumflow.controller;

import com.se.scrumflow.entity.Backlog;
import com.se.scrumflow.service.BacklogService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backlog")
public class BacklogController {
    private final BacklogService backlogService;

    @Autowired
    public BacklogController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @PostMapping("/create")
    public ResponseEntity<Backlog> createBacklog(@RequestBody Backlog backlog) {
        Backlog createdBacklog = backlogService.createBacklog(backlog);
        return new ResponseEntity<>(createdBacklog, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Backlog> getBacklogById(@PathVariable ObjectId id) {
        Backlog backlog = backlogService.getBacklogById(id);
        if (backlog!= null) {
            return new ResponseEntity<>(backlog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Backlog> updateBacklog(@RequestBody Backlog backlog) {
        Backlog updatedBacklog = backlogService.updateBacklog(backlog);
        if (updatedBacklog!= null) {
            return new ResponseEntity<>(updatedBacklog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBacklog(@PathVariable ObjectId id) {
        backlogService.deleteBacklog(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Backlog>> getAllBacklogs() {
        List<Backlog> backlogs = backlogService.getAllBacklogs();
        return new ResponseEntity<>(backlogs, HttpStatus.OK);
    }
}

