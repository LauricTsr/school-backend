package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, String> {
}
