package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
}