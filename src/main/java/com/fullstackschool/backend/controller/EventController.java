package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Event;
import com.fullstackschool.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping
    public List<Event> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Event create(@RequestBody Event event) {
        return service.save(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Integer id, @RequestBody Event details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setTitle(details.getTitle());
                    existing.setDescription(details.getDescription());
                    existing.setStartTime(details.getStartTime());
                    existing.setEndTime(details.getEndTime());
                    existing.setSchoolClass(details.getSchoolClass());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return service.findById(id)
                .map(existing -> {
                    service.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

