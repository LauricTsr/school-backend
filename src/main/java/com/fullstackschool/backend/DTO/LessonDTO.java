package com.fullstackschool.backend.DTO;

import com.fullstackschool.backend.entity.Day;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Integer id;
    private String name;
    private Day day;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer subjectId;
    private Integer classId;
    private String teacherId;
    private List<Integer> examIds;
    private List<Integer> assignmentIds;
    private List<Integer> attendanceIds;
}

