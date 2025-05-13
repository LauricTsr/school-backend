package com.fullstackschool.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "grade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer level;

    @OneToMany(mappedBy = "grade")
    private List<Student> students;

    @OneToMany(mappedBy = "grade")
    private List<SchoolClass> classes;
}

