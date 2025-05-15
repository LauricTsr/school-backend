package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
