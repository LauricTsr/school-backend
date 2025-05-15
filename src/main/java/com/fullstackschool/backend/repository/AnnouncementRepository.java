package com.fullstackschool.backend.repository;

import com.fullstackschool.backend.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
