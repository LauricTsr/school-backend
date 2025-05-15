package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Integer> {
}