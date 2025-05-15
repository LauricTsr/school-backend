package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Exam;
import com.fullstackschool.backend.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService service;

    @GetMapping
    public List<Exam> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Exam create(@RequestBody Exam exam) {
        return service.save(exam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> update(@PathVariable Integer id, @RequestBody Exam details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setTitle(details.getTitle());
                    existing.setStartTime(details.getStartTime());
                    existing.setEndTime(details.getEndTime());
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

