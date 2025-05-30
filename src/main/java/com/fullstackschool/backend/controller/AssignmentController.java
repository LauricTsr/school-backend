package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.AssignmentDTO;
import com.fullstackschool.backend.mapper.AssignmentMapper;
import com.fullstackschool.backend.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
class AssignmentController {
    @Autowired private AssignmentService service;
    @Autowired private AssignmentMapper mapper;

    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AssignmentDTO> create(@RequestBody AssignmentDTO dto) {
        return ResponseEntity.ok(mapper.toDTO(service.save(mapper.toEntity(dto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDTO> update(@PathVariable Integer id, @RequestBody AssignmentDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateAssignmentFromDto(dto, existing);
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