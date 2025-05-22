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
    public ResponseEntity<TeacherDTO> create(@RequestBody TeacherDTO dto) {
        Teacher teacher = teacherMapper.toEntity(dto);
        Teacher saved = service.save(teacher);
        return ResponseEntity.ok(teacherMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable String id, @RequestBody TeacherDTO dto) {
        return service.findById(id)
                .map(existing -> {
                    teacherMapper.updateTeacherFromDto(dto, existing);
                    Teacher updated = service.save(existing);
                    return ResponseEntity.ok(teacherMapper.toDTO(updated));
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
