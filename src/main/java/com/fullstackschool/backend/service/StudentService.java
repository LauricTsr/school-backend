package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Student;
import com.fullstackschool.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Optional<Student> findById(String id) {
        return repo.findById(id);
    }

    public Student save(Student student) {
        return repo.save(student);
    }

    public Student update(String id, Student updated) {
        updated.setId(id);
        return repo.save(updated);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}

