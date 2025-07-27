package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.ExamDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.ExamMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(NoSecurityConfig.class)
@Transactional
public class ExamControllerTest {

    @Autowired
    private ExamMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private ExamRepository repository;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private ObjectMapper objectMapper;

    private Exam exam;
    private Lesson lesson;

    @BeforeEach
    void setup() {
        Teacher teacher = new Teacher("teacher1", "teacher1", "John", "Doe", "john@mail.com", "123456", "123 rue", "img.png", "O+", UserSex.MALE, LocalDateTime.now(), LocalDateTime.of(1990, 1, 1, 0, 0), List.of(), List.of(), List.of());
        teacherRepository.saveAndFlush(teacher);


        Subject subject = new Subject(null, "Mathematics", List.of(),List.of());
        subjectRepository.saveAndFlush(subject);

        Grade grade = new Grade(null, 1, List.of(), List.of());
        gradeRepository.saveAndFlush(grade);

        SchoolClass schoolClass = new SchoolClass(null, "Class A", 30, teacher, List.of(), List.of(), grade, List.of(), List.of());
        schoolClassRepository.saveAndFlush(schoolClass);

        lesson = new Lesson(null, "Algebra", Day.MONDAY, LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                subject, schoolClass, teacher, List.of(), List.of(), List.of());
        lessonRepository.saveAndFlush(lesson);

        exam = new Exam(null, "Exam1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), lesson, List.of());

        repository.saveAndFlush(exam);
    }

    @Test
    void shouldReturnAllExams() throws Exception {
        mockMvc.perform(get("/api/exams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnExamById() throws Exception {
        mockMvc.perform(get("/api/exams/" + exam.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Exam1"));
    }

    @Test
    void shouldCreateExam() throws Exception {
        ExamDTO newExam = new ExamDTO(null, "Exam2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), lesson.getId(), List.of());

        mockMvc.perform(post("/api/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newExam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Exam2")).andExpect(jsonPath("$.lessonId").exists());
    }

    @Test
    void shouldUpdateExam() throws Exception {
        exam.setTitle("TEST");
        ExamDTO dto = mapper.toDTO(exam);
        mockMvc.perform(put("/api/exams/" + exam.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("TEST")).andExpect(jsonPath("$.lessonId").exists());
    }

    @Test
    void shouldDeleteExam() throws Exception {
        mockMvc.perform(delete("/api/exams/" + exam.getId()))
                .andExpect(status().isNoContent());
    }

}
