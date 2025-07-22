package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.StudentDTO;
import com.fullstackschool.backend.DTO.TeacherDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.Teacher;
import com.fullstackschool.backend.entity.UserSex;
import com.fullstackschool.backend.mapper.StudentMapper;
import com.fullstackschool.backend.mapper.TeacherMapper;
import com.fullstackschool.backend.repository.TeacherRepository;
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
class TeacherControllerTest {

    @Autowired private TeacherMapper mapper;

    @Autowired private MockMvc mockMvc;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private ObjectMapper objectMapper;

    private Teacher teacher;

    @BeforeEach
    void setup() {
        teacher = new Teacher();
        teacher.setId("teacher1");
        teacher.setUsername("teacher1");
        teacher.setName("John");
        teacher.setSurname("Doe");
        teacher.setEmail("john.doe@example.com");
        teacher.setPhone("123456789");
        teacher.setAddress("123 Main St");
        teacher.setImg("profile.png");
        teacher.setBloodType("O+");
        teacher.setSex(UserSex.MALE);
        teacher.setBirthday(LocalDateTime.of(1990, 1, 1, 0, 0));
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setSubjects(Collections.emptyList());
        teacher.setLessons(Collections.emptyList());
        teacher.setSchoolClasses(Collections.emptyList());

        teacherRepository.save(teacher);
        teacherRepository.flush();
    }

    @Test
    void shouldReturnAllTeachers() throws Exception {
        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnTeacherById() throws Exception {
        mockMvc.perform(get("/api/teachers/teacher1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("teacher1"));
    }

    @Test
    void shouldCreateTeacher() throws Exception {
        TeacherDTO newTeacher = new TeacherDTO(
                "teacher2", "jane.doe", "Jane", "Doe", "jane@example.com", "987654321",
                "456 Side St","img.png", "A+", UserSex.FEMALE, LocalDateTime.now(), LocalDateTime.of(1992, 2, 2, 0, 0),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTeacher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("teacher2"));
    }

    @Test
    void shouldUpdateTeacher() throws Exception {
        teacher.setName("UpdatedName");
        TeacherDTO dto = mapper.toDTO(teacher);
        mockMvc.perform(put("/api/teachers/teacher1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"));
    }

    @Test
    void shouldDeleteTeacher() throws Exception {
        mockMvc.perform(delete("/api/teachers/teacher1"))
                .andExpect(status().isNoContent());
    }
}
