package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
