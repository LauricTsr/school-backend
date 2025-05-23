package com.fullstackschool.backend.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private Integer id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer lessonId;
    private List<Integer> resultIds;
}
