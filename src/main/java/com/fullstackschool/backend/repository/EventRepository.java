package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}