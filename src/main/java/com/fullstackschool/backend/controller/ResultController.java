package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Result;
import com.fullstackschool.backend.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService service;

    @GetMapping
    public List<Result> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Result create(@RequestBody Result result) {
        return service.save(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> update(@PathVariable Integer id, @RequestBody Result details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setScore(details.getScore());
                    existing.setExam(details.getExam());
                    existing.setAssignment(details.getAssignment());
                    existing.setStudent(details.getStudent());
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

