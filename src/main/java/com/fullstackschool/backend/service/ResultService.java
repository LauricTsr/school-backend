package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Result;
import com.fullstackschool.backend.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    @Autowired
    private ResultRepository repository;

    public List<Result> findAll() {
        return repository.findAll();
    }

    public Optional<Result> findById(Integer id) {
        return repository.findById(id);
    }

    public Result save(Result result) {
        return repository.save(result);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}