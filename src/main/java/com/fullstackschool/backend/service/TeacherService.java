package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Teacher;
import com.fullstackschool.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository repository;

    public List<Teacher> findAll() {
        return repository.findAll();
    }

    public Optional<Teacher> findById(String id) {
        return repository.findById(id);
    }

    public Teacher save(Teacher teacher) {
        return repository.save(teacher);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
