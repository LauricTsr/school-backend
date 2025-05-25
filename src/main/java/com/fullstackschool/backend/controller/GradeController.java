package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.GradeDTO;
import com.fullstackschool.backend.mapper.GradeMapper;
import com.fullstackschool.backend.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
class GradeController {
    @Autowired private GradeService service;
    @Autowired private GradeMapper mapper;

    @GetMapping
    public ResponseEntity<List<GradeDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GradeDTO> create(@RequestBody GradeDTO dto) {
        return ResponseEntity.ok(mapper.toDTO(service.save(mapper.toEntity(dto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> update(@PathVariable Integer id, @RequestBody GradeDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateGradeFromDto(dto, existing);
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
