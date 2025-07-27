// Exemple de test d'intégration pour les autres contrôleurs

// ResultControllerTest
package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.ResultDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.ResultMapper;
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
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(NoSecurityConfig.class)
@Transactional
class ResultControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ResultRepository repository;
    @Autowired private ExamRepository examRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private ParentRepository parentRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private AssignmentRepository assignmentRepository;
    @Autowired private ResultMapper mapper;


    @Autowired private ObjectMapper objectMapper;

    private Exam exam;
    private Assignment assignment;
    private Student student;
    private Result result;

    @BeforeEach
    void setup() {
        Teacher teacher = new Teacher("teacher1", "teacher1", "John", "Doe", "john@mail.com", "123456", "123 rue", "img.png", "O+", UserSex.MALE, LocalDateTime.now(), LocalDateTime.of(1990, 1, 1, 0, 0), List.of(), List.of(), List.of());
        teacherRepository.saveAndFlush(teacher);

        Grade grade = new Grade();
        grade.setLevel(1);
        grade = gradeRepository.saveAndFlush(grade);

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName("Test Class");
        schoolClass.setCapacity(30);
        schoolClass.setGrade(grade);
        schoolClass = schoolClassRepository.saveAndFlush(schoolClass);

        Parent parent = new Parent();
        parent.setId("parent1");
        parent.setUsername("parent1");
        parent.setName("Jane");
        parent.setSurname("Doe");
        parent.setPhone("123456789");
        parent.setAddress("456 Main St");
        parent.setCreatedAt(LocalDateTime.now());
        parent = parentRepository.saveAndFlush(parent);

        Subject subject = new Subject(null, "Mathematics", List.of(),List.of());
        subjectRepository.saveAndFlush(subject);

        Lesson lesson = new Lesson(null, "Algebra", Day.MONDAY, LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                subject, schoolClass, teacher, List.of(), List.of(), List.of());
        lessonRepository.saveAndFlush(lesson);

        exam = new Exam(null, "exam", LocalDateTime.now(), LocalDateTime.now().plusHours(1), lesson, List.of());
        examRepository.saveAndFlush(exam);

        assignment = new Assignment(null, "assignment", LocalDateTime.now(), LocalDateTime.now().plusHours(1), lesson, List.of());
        assignmentRepository.saveAndFlush(assignment);

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
        studentRepository.saveAndFlush(student);

        result = new Result(null, 1, exam, assignment, student);

        result = repository.saveAndFlush(result);
    }

    @Test
    void shouldReturnAllResults() throws Exception {
        mockMvc.perform(get("/api/results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnResultById() throws Exception {
        mockMvc.perform(get("/api/results/" + result.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(1));
    }

    @Test
    void shouldCreateResult() throws Exception {
        ResultDTO newResult = new ResultDTO(null,6, exam.getId(),assignment.getId(),student.getId());

        mockMvc.perform(post("/api/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newResult)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(6)).andExpect(jsonPath("$.examId").exists()).andExpect(jsonPath("$.assignmentId").exists()).andExpect(jsonPath("$.studentId").exists());

    }

    @Test
    void shouldUpdateResult() throws Exception {
        result.setScore(8);
        ResultDTO dto = mapper.toDTO(result);
        mockMvc.perform(put("/api/results/" + result.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(8)).andExpect(jsonPath("$.examId").exists()).andExpect(jsonPath("$.assignmentId").exists()).andExpect(jsonPath("$.studentId").exists());
    }

    @Test
    void shouldDeleteResult() throws Exception {
        mockMvc.perform(delete("/api/results/" + result.getId()))
                .andExpect(status().isNoContent());
    }
}