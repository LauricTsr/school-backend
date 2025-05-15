package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Subject;
import com.fullstackschool.backend.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository repository;

    public List<Subject> findAll() {
        return repository.findAll();
    }

    public Optional<Subject> findById(Integer id) {
        return repository.findById(id);
    }

    public Subject save(Subject subject) {
        return repository.save(subject);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
