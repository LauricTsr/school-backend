package com.fullstackschool.backend.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private Integer id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Integer lessonId;
    private List<Integer> resultIds;
}

