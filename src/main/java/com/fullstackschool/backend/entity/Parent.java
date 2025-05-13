package com.fullstackschool.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;
    private String surname;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    private String address;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "parent")
    private List<Student> students;
}
