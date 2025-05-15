package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.SchoolClass;
import com.fullstackschool.backend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService service;

    @GetMapping
    public List<SchoolClass> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SchoolClass create(@RequestBody SchoolClass schoolClass) {
        return service.save(schoolClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClass> update(@PathVariable Integer id, @RequestBody SchoolClass details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setName(details.getName());
                    existing.setCapacity(details.getCapacity());
                    existing.setSupervisor(details.getSupervisor());
                    existing.setGrade(details.getGrade());
                    existing.setLessons(details.getLessons());
                    existing.setStudents(details.getStudents());
                    existing.setEvents(details.getEvents());
                    existing.setAnnouncements(details.getAnnouncements());
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

