package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Attendance;
import com.fullstackschool.backend.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository repository;

    public List<Attendance> findAll() {
        return repository.findAll();
    }

    public Optional<Attendance> findById(Integer id) {
        return repository.findById(id);
    }

    public Attendance save(Attendance attendance) {
        return repository.save(attendance);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
