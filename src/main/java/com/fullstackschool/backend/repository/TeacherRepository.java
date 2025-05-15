package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
}
