package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<SchoolClass, Integer> {
}