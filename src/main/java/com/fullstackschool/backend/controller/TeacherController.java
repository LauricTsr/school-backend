package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Teacher;
import com.fullstackschool.backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @GetMapping
    public List<Teacher> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Teacher create(@RequestBody Teacher teacher) {
        return service.save(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable String id, @RequestBody Teacher details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setName(details.getName());
                    existing.setSurname(details.getSurname());
                    existing.setEmail(details.getEmail());
                    existing.setPhone(details.getPhone());
                    existing.setAddress(details.getAddress());
                    existing.setImg(details.getImg());
                    existing.setBloodType(details.getBloodType());
                    existing.setSex(details.getSex());
                    existing.setBirthday(details.getBirthday());
                    existing.setSubjects(details.getSubjects());
                    existing.setLessons(details.getLessons());
                    existing.setClasses(details.getClasses());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.findById(id)
                .map(existing -> {
                    service.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
