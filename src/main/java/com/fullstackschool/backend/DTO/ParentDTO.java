package com.fullstackschool.backend.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDTO {
    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private List<String> studentIds;
}

