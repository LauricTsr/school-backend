package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.StudentDTO;
import com.fullstackschool.backend.entity.Student;
import com.fullstackschool.backend.mapper.StudentMapper;
import com.fullstackschool.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
class StudentController {
    @Autowired
    private StudentService service;
    @Autowired
    ParentService parentService;
    @Autowired
    SchoolClassService schoolClassService;
    @Autowired
    GradeService gradeService;
    @Autowired private StudentMapper mapper;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable String id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO dto) {
        Student student = mapper.toEntity(dto);
        student.setParent(parentService.findById(dto.getParentId()).orElseThrow());
        student.setSchoolClass(schoolClassService.findById(dto.getClassId()).orElseThrow());
        student.setGrade(gradeService.findById(dto.getGradeId()).orElseThrow());

        return ResponseEntity.ok(mapper.toDTO(service.save(student)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable String id, @RequestBody StudentDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateStudentFromDto(dto, existing);
            existing.setParent(parentService.findById(dto.getParentId()).orElseThrow());
            existing.setSchoolClass(schoolClassService.findById(dto.getClassId()).orElseThrow());
            existing.setGrade(gradeService.findById(dto.getGradeId()).orElseThrow());
            return ResponseEntity.ok(mapper.toDTO(service.save(existing)));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.findById(id).map(existing -> {
            service.delete(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


