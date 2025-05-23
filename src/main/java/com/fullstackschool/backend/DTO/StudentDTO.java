package com.fullstackschool.backend.DTO;

import com.fullstackschool.backend.entity.UserSex;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String img;
    private String bloodType;
    private UserSex sex;
    private LocalDateTime createdAt;
    private LocalDateTime birthday;
    private String parentId;
    private Integer classId;
    private Integer gradeId;
    private List<Integer> attendanceIds;
    private List<Integer> resultIds;
}

