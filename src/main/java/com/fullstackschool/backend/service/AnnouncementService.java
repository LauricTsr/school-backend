package com.fullstackschool.backend.service;

import com.fullstackschool.backend.entity.Announcement;
import com.fullstackschool.backend.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository repository;

    public List<Announcement> findAll() {
        return repository.findAll();
    }

    public Optional<Announcement> findById(Integer id) {
        return repository.findById(id);
    }

    public Announcement save(Announcement announcement) {
        return repository.save(announcement);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }}
