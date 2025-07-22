package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.LessonDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.LessonMapper;
import com.fullstackschool.backend.repository.*;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Import(NoSecurityConfig.class)
@Transactional
class LessonControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private LessonMapper mapper;

    private Lesson lesson;
    private Teacher teacher;
    private SchoolClass schoolClass;
    private Subject subject;

    @BeforeEach
    void setup() {
        teacher = new Teacher("teacher1", "teacher1", "John", "Doe", "john@mail.com", "123456", "123 rue", "img.png", "O+", UserSex.MALE, LocalDateTime.now(), LocalDateTime.of(1990, 1, 1, 0, 0), List.of(), List.of(), List.of());
        teacherRepository.saveAndFlush(teacher);

        subject = new Subject(null, "Mathematics", List.of(),List.of());
        subjectRepository.saveAndFlush(subject);

        Grade grade = new Grade(null, 1, List.of(), List.of());
        gradeRepository.saveAndFlush(grade);

        schoolClass = new SchoolClass(null, "Class A", 30, teacher, List.of(), List.of(), grade, List.of(), List.of());
        schoolClassRepository.saveAndFlush(schoolClass);

        lesson = new Lesson(null, "Algebra", Day.MONDAY, LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                subject, schoolClass, teacher, List.of(), List.of(), List.of());
        lessonRepository.saveAndFlush(lesson);
    }

    @Test
    void shouldReturnAllLessons() throws Exception {
        mockMvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnLessonById() throws Exception {
        mockMvc.perform(get("/api/lessons/" + lesson.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Algebra"));
    }

    @Test
    void shouldCreateLesson() throws Exception {
        LessonDTO dto = new LessonDTO(null, "Geometry", Day.TUESDAY,
                LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                subject.getId(), schoolClass.getId(), teacher.getId(),
                List.of(), List.of(), List.of());

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Geometry")).andExpect(jsonPath("$.classId").exists()).andExpect(jsonPath("$.teacherId").exists()).andExpect(jsonPath("$.subjectId").exists());
    }

    @Test
    void shouldUpdateLesson() throws Exception {
        lesson.setName("Updated Algebra");
        LessonDTO dto = mapper.toDTO(lesson);

        mockMvc.perform(put("/api/lessons/" + lesson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Algebra")).andExpect(jsonPath("$.classId").exists()).andExpect(jsonPath("$.teacherId").exists()).andExpect(jsonPath("$.subjectId").exists());
    }

    @Test
    void shouldDeleteLesson() throws Exception {
        mockMvc.perform(delete("/api/lessons/" + lesson.getId()))
                .andExpect(status().isNoContent());
    }
}