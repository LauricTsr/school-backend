package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Assignment;
import com.fullstackschool.backend.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService service;

    @GetMapping
    public List<Assignment> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Assignment create(@RequestBody Assignment assignment) {
        return service.save(assignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> update(@PathVariable Integer id, @RequestBody Assignment details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setTitle(details.getTitle());
                    existing.setStartDate(details.getStartDate());
                    existing.setDueDate(details.getDueDate());
                    existing.setLesson(details.getLesson());
                    existing.setResults(details.getResults());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return service.findById(id)
                .map(existing -> {
                    service.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}