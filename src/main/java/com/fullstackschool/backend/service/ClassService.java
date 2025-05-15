package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.SchoolClass;
import com.fullstackschool.backend.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository repository;

    public List<SchoolClass> findAll() {
        return repository.findAll();
    }

    public Optional<SchoolClass> findById(Integer id) {
        return repository.findById(id);
    }

    public SchoolClass save(SchoolClass schoolClass) {
        return repository.save(schoolClass);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
