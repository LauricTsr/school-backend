package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Grade;
import com.fullstackschool.backend.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    @Autowired
    private GradeRepository repository;

    public List<Grade> findAll() {
        return repository.findAll();
    }

    public Optional<Grade> findById(Integer id) {
        return repository.findById(id);
    }

    public Grade save(Grade grade) {
        return repository.save(grade);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
