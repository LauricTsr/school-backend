package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.StudentDTO;
import com.fullstackschool.backend.DTO.SchoolClassDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.StudentMapper;
import com.fullstackschool.backend.mapper.SchoolClassMapper;
import com.fullstackschool.backend.repository.GradeRepository;
import com.fullstackschool.backend.repository.SchoolClassRepository;
import com.fullstackschool.backend.repository.SubjectRepository;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Import(NoSecurityConfig.class)
@Transactional
class SchoolClassControllerTest {

    @Autowired private SchoolClassMapper mapper;

    @Autowired private MockMvc mockMvc;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private ObjectMapper objectMapper;

    private SchoolClass schoolClass;
    private Grade grade;
    private Teacher teacher;

    @BeforeEach
    void setup() {
        teacher = new Teacher("teacher1", "teacher1", "John", "Doe", "john@mail.com", "123456", "123 rue", "img.png", "O+", UserSex.MALE, LocalDateTime.now(), LocalDateTime.of(1990, 1, 1, 0, 0), List.of(), List.of(), List.of());
        teacherRepository.saveAndFlush(teacher);

        Subject subject = new Subject(null, "Mathematics", List.of(),List.of());
        subjectRepository.saveAndFlush(subject);

        grade = new Grade(null, 1, List.of(), List.of());
        gradeRepository.saveAndFlush(grade);

        schoolClass = new SchoolClass(null, "Class A", 30, teacher, List.of(), List.of(), grade, List.of(), List.of());
        schoolClassRepository.saveAndFlush(schoolClass);
    }

    @Test
    void shouldReturnAllSchoolClasses() throws Exception {
        mockMvc.perform(get("/api/school-classes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnSchoolClassById() throws Exception {
        mockMvc.perform(get("/api/school-classes/" + schoolClass.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Class A"));
    }

    @Test
    void shouldCreateSchoolClass() throws Exception {
        SchoolClassDTO newSchoolClass = new SchoolClassDTO(null, "Class E", 32, teacher.getId(), grade.getId(), List.of(), List.of(), List.of(), List.of());

        mockMvc.perform(post("/api/school-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSchoolClass)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Class E")).andExpect(jsonPath("$.gradeId").exists()).andExpect(jsonPath("$.supervisorId").exists());
    }

    @Test
    void shouldUpdateSchoolClass() throws Exception {
        schoolClass.setName("Class C");
        SchoolClassDTO dto = mapper.toDTO(schoolClass);
        mockMvc.perform(put("/api/school-classes/" + schoolClass.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Class C")).andExpect(jsonPath("$.gradeId").exists()).andExpect(jsonPath("$.supervisorId").exists());
    }

    @Test
    void shouldDeleteSchoolClass() throws Exception {
        mockMvc.perform(delete("/api/school-classes/" + schoolClass.getId()))
                .andExpect(status().isNoContent());
    }
}
