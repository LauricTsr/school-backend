package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Exam;
import com.fullstackschool.backend.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {
    @Autowired
    private ExamRepository repository;

    public List<Exam> findAll() {
        return repository.findAll();
    }

    public Optional<Exam> findById(Integer id) {
        return repository.findById(id);
    }

    public Exam save(Exam exam) {
        return repository.save(exam);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
