package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Event;
import com.fullstackschool.backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    public List<Event> findAll() {
        return repository.findAll();
    }

    public Optional<Event> findById(Integer id) {
        return repository.findById(id);
    }

    public Event save(Event event) {
        return repository.save(event);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}