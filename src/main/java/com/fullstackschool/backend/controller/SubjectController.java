package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Subject;
import com.fullstackschool.backend.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService service;

    @GetMapping
    public List<Subject> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Subject create(@RequestBody Subject subject) {
        return service.save(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> update(@PathVariable Integer id, @RequestBody Subject details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setName(details.getName());
                    existing.setTeachers(details.getTeachers());
                    existing.setLessons(details.getLessons());
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
