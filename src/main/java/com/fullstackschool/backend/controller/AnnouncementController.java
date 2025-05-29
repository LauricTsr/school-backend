package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.AnnouncementDTO;
import com.fullstackschool.backend.entity.Announcement;
import com.fullstackschool.backend.entity.Student;
import com.fullstackschool.backend.mapper.AnnouncementMapper;
import com.fullstackschool.backend.service.AnnouncementService;
import com.fullstackschool.backend.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
class AnnouncementController {
    @Autowired private AnnouncementService service;
    @Autowired private AnnouncementMapper mapper;
    @Autowired
    SchoolClassService schoolClassService;

    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnnouncementDTO> create(@RequestBody AnnouncementDTO dto) {
        Announcement announcement = mapper.toEntity(dto);
        announcement.setSchoolClass(schoolClassService.findById(dto.getClassId()).orElseThrow());
        return ResponseEntity.ok(mapper.toDTO(service.save(announcement)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> update(@PathVariable Integer id, @RequestBody AnnouncementDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateAnnouncementFromDto(dto, existing);
            existing.setSchoolClass(schoolClassService.findById(dto.getClassId()).orElseThrow());
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
