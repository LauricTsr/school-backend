package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Announcement;
import com.fullstackschool.backend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    @GetMapping
    public List<Announcement> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Announcement create(@RequestBody Announcement announcement) {
        return service.save(announcement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Announcement> update(@PathVariable Integer id, @RequestBody Announcement details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setTitle(details.getTitle());
                    existing.setDescription(details.getDescription());
                    existing.setDate(details.getDate());
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
