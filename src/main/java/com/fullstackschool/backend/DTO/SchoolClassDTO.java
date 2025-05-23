package com.fullstackschool.backend.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassDTO {
    private Integer id;
    private String name;
    private Integer capacity;
    private String supervisorId;
    private Integer gradeId;
    private List<Integer> lessonIds;
    private List<String> studentIds;
    private List<Integer> eventIds;
    private List<Integer> announcementIds;
}

