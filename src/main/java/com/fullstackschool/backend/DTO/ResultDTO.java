package com.fullstackschool.backend.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private Integer id;
    private Integer score;
    private Integer examId;
    private Integer assignmentId;
    private String studentId;
}

