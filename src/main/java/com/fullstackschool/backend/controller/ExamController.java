package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.ExamDTO;
import com.fullstackschool.backend.entity.Exam;
import com.fullstackschool.backend.mapper.ExamMapper;
import com.fullstackschool.backend.service.ExamService;
import com.fullstackschool.backend.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
class ExamController {
    @Autowired
    private ExamService service;
    @Autowired
    LessonService lessonService;
    @Autowired
    private ExamMapper mapper;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExamDTO> create(@RequestBody ExamDTO dto) {
        Exam exam = mapper.toEntity(dto);
        exam.setLesson(lessonService.findById(dto.getLessonId()).orElseThrow());
        return ResponseEntity.ok(mapper.toDTO(service.save(exam)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> update(@PathVariable Integer id, @RequestBody ExamDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateExamFromDto(dto, existing);
            existing.setLesson(lessonService.findById(dto.getLessonId()).orElseThrow());
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


