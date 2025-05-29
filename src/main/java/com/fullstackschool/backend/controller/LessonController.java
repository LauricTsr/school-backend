package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.LessonDTO;
import com.fullstackschool.backend.entity.Lesson;
import com.fullstackschool.backend.mapper.LessonMapper;
import com.fullstackschool.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
class LessonController {
    @Autowired private LessonService service;
    @Autowired private LessonMapper mapper;
    @Autowired
    SubjectService subjectService;
    @Autowired
    SchoolClassService schoolClassService;
    @Autowired
    TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LessonDTO> create(@RequestBody LessonDTO dto) {
        Lesson lesson = mapper.toEntity(dto);
        lesson.setSubject(subjectService.findById(dto.getSubjectId()).orElseThrow());
        lesson.setSchoolClass(schoolClassService.findById(dto.getClassId()).orElseThrow());
        lesson.setTeacher(teacherService.findById(dto.getTeacherId()).orElseThrow());
        return ResponseEntity.ok(mapper.toDTO(service.save(mapper.toEntity(dto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> update(@PathVariable Integer id, @RequestBody LessonDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateLessonFromDto(dto, existing);
            existing.setSubject(subjectService.findById(dto.getSubjectId()).orElseThrow());
            existing.setSchoolClass(schoolClassService.findById(dto.getClassId()).orElseThrow());
            existing.setTeacher(teacherService.findById(dto.getTeacherId()).orElseThrow());
            return ResponseEntity.ok(mapper.toDTO(service.save(existing)));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return service.findById(id).map(existing -> {
            service.delete(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

