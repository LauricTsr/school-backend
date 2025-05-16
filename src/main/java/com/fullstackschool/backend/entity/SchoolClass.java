package com.fullstackschool.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "school_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Teacher supervisor;

    @OneToMany(mappedBy = "schoolClass")
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "schoolClass")
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @OneToMany(mappedBy = "schoolClass")
    private List<Event> events;

    @OneToMany(mappedBy = "schoolClass")
    private List<Announcement> announcements;
}



