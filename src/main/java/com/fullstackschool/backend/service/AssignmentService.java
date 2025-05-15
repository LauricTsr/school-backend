package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Assignment;
import com.fullstackschool.backend.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository repository;

    public List<Assignment> findAll() {
        return repository.findAll();
    }

    public Optional<Assignment> findById(Integer id) {
        return repository.findById(id);
    }

    public Assignment save(Assignment assignment) {
        return repository.save(assignment);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
