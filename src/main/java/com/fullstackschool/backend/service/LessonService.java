package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Lesson;
import com.fullstackschool.backend.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    private LessonRepository repository;

    public List<Lesson> findAll() {
        return repository.findAll();
    }

    public Optional<Lesson> findById(Integer id) {
        return repository.findById(id);
    }

    public Lesson save(Lesson lesson) {
        return repository.save(lesson);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}