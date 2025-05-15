package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Grade;
import com.fullstackschool.backend.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService service;

    @GetMapping
    public List<Grade> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Grade create(@RequestBody Grade grade) {
        return service.save(grade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> update(@PathVariable Integer id, @RequestBody Grade details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setLevel(details.getLevel());
                    existing.setStudents(details.getStudents());
                    existing.setClasses(details.getClasses());
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
