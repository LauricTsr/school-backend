package com.fullstackschool.backend.controller;

import com.fullstackschool.backend.entity.Parent;
import com.fullstackschool.backend.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentService service;

    @GetMapping
    public List<Parent> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parent> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Parent create(@RequestBody Parent parent) {
        return service.save(parent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parent> update(@PathVariable String id, @RequestBody Parent details) {
        return service.findById(id)
                .map(existing -> {
                    existing.setName(details.getName());
                    existing.setSurname(details.getSurname());
                    existing.setEmail(details.getEmail());
                    existing.setPhone(details.getPhone());
                    existing.setAddress(details.getAddress());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.findById(id)
                .map(existing -> {
                    service.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

