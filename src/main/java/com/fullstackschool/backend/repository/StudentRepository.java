package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
