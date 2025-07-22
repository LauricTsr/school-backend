package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.DTO.EventDTO;
import com.fullstackschool.backend.entity.Event;
import com.fullstackschool.backend.mapper.EventMapper;
import com.fullstackschool.backend.service.EventService;
import com.fullstackschool.backend.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
class EventController {
    @Autowired
    private EventService service;
    @Autowired
    SchoolClassService schoolClassService;

    @Autowired
    private EventMapper mapper;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Integer id) {
        return service.findById(id).map(mapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@RequestBody EventDTO dto) {
        Event event = mapper.toEntity(dto);
        event.setSchoolClass(schoolClassService.findById(dto.getClassId()).orElseThrow());
        return ResponseEntity.ok(mapper.toDTO(service.save(event)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Integer id, @RequestBody EventDTO dto) {
        return service.findById(id).map(existing -> {
            mapper.updateEventFromDto(dto, existing);
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

