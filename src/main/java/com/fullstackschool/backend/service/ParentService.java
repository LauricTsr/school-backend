package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Parent;
import com.fullstackschool.backend.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentService {
    @Autowired
    private ParentRepository repository;

    public List<Parent> findAll() {
        return repository.findAll();
    }

    public Optional<Parent> findById(String id) {
        return repository.findById(id);
    }

    public Parent save(Parent parent) {
        return repository.save(parent);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
