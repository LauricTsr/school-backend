package com.fullstackschool.backend.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime date;
    private Integer classId;
}

