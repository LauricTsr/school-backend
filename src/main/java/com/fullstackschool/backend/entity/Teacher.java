package com.fullstackschool.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"schoolClasses", "lessons", "subjects"})
public class Teacher {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;
    private String surname;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String address;
    private String img;
    private String bloodType;

    @Enumerated(EnumType.STRING)
    private UserSex sex;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime birthday;

    @ManyToMany
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;

    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "supervisor")
    private List<SchoolClass> schoolClasses;
}


