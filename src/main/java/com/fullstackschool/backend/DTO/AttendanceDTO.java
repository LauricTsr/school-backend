package com.fullstackschool.backend.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Integer id;
    private LocalDateTime date;
    private Boolean present;
    private String studentId;
    private Integer lessonId;
}

