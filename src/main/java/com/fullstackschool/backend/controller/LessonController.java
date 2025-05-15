package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Lesson;
import com.fullstackschool.backend.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService service;

    @GetMapping
    public List<Lesson> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Lesson create(@RequestBody Lesson lesson) {
        return service.save(lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> update(@PathVariable Integer id, @RequestBody Lesson details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setName(details.getName());
                    existing.setDay(details.getDay());
                    existing.setStartTime(details.getStartTime());
                    existing.setEndTime(details.getEndTime());
                    existing.setSubject(details.getSubject());
                    existing.setSchoolClass(details.getSchoolClass());
                    existing.setTeacher(details.getTeacher());
                    existing.setExams(details.getExams());
                    existing.setAssignments(details.getAssignments());
                    existing.setAttendances(details.getAttendances());
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

