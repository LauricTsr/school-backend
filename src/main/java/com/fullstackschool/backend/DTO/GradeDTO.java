package com.fullstackschool.backend.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private Integer id;
    private Integer level;
    private List<String> studentIds;
    private List<Integer> classIds;
}

