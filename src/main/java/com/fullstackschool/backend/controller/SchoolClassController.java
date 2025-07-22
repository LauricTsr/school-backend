package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.SchoolClassDTO;
import com.fullstackschool.backend.entity.SchoolClass;
import com.fullstackschool.backend.mapper.SchoolClassMapper;
import com.fullstackschool.backend.service.GradeService;
import com.fullstackschool.backend.service.ParentService;
import com.fullstackschool.backend.service.SchoolClassService;
import com.fullstackschool.backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-classes")
class SchoolClassController {
    @Autowired
    private SchoolClassService service;
    @Autowired
    TeacherService teacherService;
    @Autowired
    GradeService gradeService;
    @Autowired
    private SchoolClassMapper mapper;

    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SchoolClassDTO> create(@RequestBody SchoolClassDTO dto) {
        SchoolClass schoolClass = mapper.toEntity(dto);
        schoolClass.setGrade(gradeService.findById(dto.getGradeId()).orElseThrow());
        schoolClass.setSupervisor(teacherService.findById(dto.getSupervisorId()).orElseThrow());
        return ResponseEntity.ok(mapper.toDTO(service.save(schoolClass)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> update(@PathVariable Integer id, @RequestBody SchoolClassDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateSchoolClassFromDto(dto, existing);
            existing.setGrade(gradeService.findById(dto.getGradeId()).orElseThrow());
            existing.setSupervisor(teacherService.findById(dto.getSupervisorId()).orElseThrow());
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

