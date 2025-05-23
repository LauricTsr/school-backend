package com.fullstackschool.backend.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Integer id;
    private String name;
    private List<String> teacherIds;
    private List<Integer> lessonIds;
}

