package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.TeacherDTO;
import com.fullstackschool.backend.entity.Teacher;
import com.fullstackschool.backend.mapper.TeacherMapper;
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

    @Autowired
    private TeacherMapper teacherMapper;

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAll() {
        List<Teacher> teachers = service.findAll();
        List<TeacherDTO> dtos = teachers.stream().map(teacherMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable String id) {
        return service.findById(id)
                .map(teacherMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Teacher create(@RequestBody Teacher teacher) {
        return service.save(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable String id, @RequestBody Teacher details) {
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
                    existing.setSchoolClasses(details.getSchoolClasses());
                    return ResponseEntity.ok(teacherMapper.toDTO(service.save(existing)));
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
