package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}

