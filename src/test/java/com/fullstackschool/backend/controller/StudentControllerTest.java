// Exemple de test d'intégration pour les autres contrôleurs

// StudentControllerTest
package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.StudentDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.StudentMapper;
import com.fullstackschool.backend.repository.GradeRepository;
import com.fullstackschool.backend.repository.ParentRepository;
import com.fullstackschool.backend.repository.SchoolClassRepository;
import com.fullstackschool.backend.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Import(NoSecurityConfig.class)
@Transactional
class StudentControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private StudentRepository repository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private ParentRepository parentRepository;
    @Autowired private StudentMapper mapper;


    @Autowired private ObjectMapper objectMapper;

    private Student student;
    private Grade grade;
    private SchoolClass schoolClass;
    private Parent parent;

    @BeforeEach
    void setup() {
        grade = new Grade();
        grade.setLevel(1);
        grade = gradeRepository.saveAndFlush(grade);

        schoolClass = new SchoolClass();
        schoolClass.setName("Test Class");
        schoolClass.setCapacity(30);
        schoolClass.setGrade(grade);
        schoolClass = schoolClassRepository.saveAndFlush(schoolClass);

        parent = new Parent();
        parent.setId("parent1");
        parent.setUsername("parent1");
        parent.setName("Jane");
        parent.setSurname("Doe");
        parent.setPhone("123456789");
        parent.setAddress("456 Main St");
        parent.setCreatedAt(LocalDateTime.now());
        parent = parentRepository.saveAndFlush(parent);

        student = new Student();
        student.setId("student1");
        student.setUsername("student1");
        student.setName("Alice");
        student.setSurname("Smith");
        student.setAddress("123 Street");
        student.setSex(UserSex.FEMALE);
        student.setBloodType("A+");
        student.setCreatedAt(LocalDateTime.now());
        student.setBirthday(LocalDateTime.of(2010, 5, 5, 0, 0));
        student.setParent(parent);
        student.setSchoolClass(schoolClass);
        student.setGrade(grade);
        student.setAttendances(Collections.emptyList());
        student.setResults(Collections.emptyList());

        student = repository.saveAndFlush(student);
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnStudentById() throws Exception {
        mockMvc.perform(get("/api/students/student1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("student1"));
    }

    @Test
    void shouldCreateStudent() throws Exception {
        StudentDTO newStudent = new StudentDTO();
        newStudent.setId("student2");
        newStudent.setUsername("student2");
        newStudent.setName("Bob");
        newStudent.setSurname("Brown");
        newStudent.setAddress("456 Avenue");
        newStudent.setSex(UserSex.MALE);
        newStudent.setBloodType("B+");
        newStudent.setCreatedAt(LocalDateTime.now());
        newStudent.setBirthday(LocalDateTime.of(2011, 6, 6, 0, 0));
        newStudent.setParentId(parent.getId());
        newStudent.setClassId(schoolClass.getId());
        newStudent.setGradeId(grade.getId());

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("student2")).andExpect(jsonPath("$.classId").exists()).andExpect(jsonPath("$.parentId").exists()).andExpect(jsonPath("$.gradeId").exists());
        ;
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        student.setName("UpdatedAlice");
        StudentDTO dto = mapper.toDTO(student);
        mockMvc.perform(put("/api/students/student1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedAlice")).andExpect(jsonPath("$.classId").exists()).andExpect(jsonPath("$.parentId").exists()).andExpect(jsonPath("$.gradeId").exists());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/students/student1"))
                .andExpect(status().isNoContent());
    }
}