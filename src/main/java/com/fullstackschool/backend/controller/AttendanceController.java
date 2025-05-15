package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Attendance;
import com.fullstackschool.backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService service;

    @GetMapping
    public List<Attendance> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Attendance create(@RequestBody Attendance attendance) {
        return service.save(attendance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> update(@PathVariable Integer id, @RequestBody Attendance details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setDate(details.getDate());
                    existing.setPresent(details.getPresent());
                    existing.setStudent(details.getStudent());
                    existing.setLesson(details.getLesson());
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
