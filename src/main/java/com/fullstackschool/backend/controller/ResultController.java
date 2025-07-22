package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.ResultDTO;
import com.fullstackschool.backend.entity.Result;
import com.fullstackschool.backend.mapper.ResultMapper;
import com.fullstackschool.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
class ResultController {
    @Autowired
    private ResultService service;
    @Autowired
    ExamService examService;
    @Autowired
    AssignmentService assignmentService;
    @Autowired
    StudentService studentService;
    @Autowired
    private ResultMapper mapper;

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResultDTO> create(@RequestBody ResultDTO dto) {
        Result result = mapper.toEntity(dto);
        result.setExam(examService.findById(dto.getExamId()).orElseThrow());
        result.setAssignment(assignmentService.findById(dto.getAssignmentId()).orElseThrow());
        result.setStudent(studentService.findById(dto.getStudentId()).orElseThrow());
        return ResponseEntity.ok(mapper.toDTO(service.save(result)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> update(@PathVariable Integer id, @RequestBody ResultDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateResultFromDto(dto, existing);
            existing.setExam(examService.findById(dto.getExamId()).orElseThrow());
            existing.setAssignment(assignmentService.findById(dto.getAssignmentId()).orElseThrow());
            existing.setStudent(studentService.findById(dto.getStudentId()).orElseThrow());
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

