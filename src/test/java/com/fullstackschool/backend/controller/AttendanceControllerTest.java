package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.AttendanceDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.AttendanceMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(NoSecurityConfig.class)
@Transactional
public class AttendanceControllerTest {

    @Autowired
    private AttendanceMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private AttendanceRepository repository;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private ParentRepository parentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private ObjectMapper objectMapper;

    private Attendance attendance;
    private Lesson lesson;
    private Student student;

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

        Parent parent = new Parent("parent1", "parent1", "Jane", "Doe", "e@gmail.com", "123456789", "456 Main St", LocalDateTime.now(), List.of());
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
        student = studentRepository.saveAndFlush(student);

        attendance = new Attendance(null, LocalDateTime.now(), true, student, lesson);

        repository.saveAndFlush(attendance);
    }

    @Test
    void shouldReturnAllAttendances() throws Exception {
        mockMvc.perform(get("/api/attendances"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnAttendanceById() throws Exception {
        mockMvc.perform(get("/api/attendances/" + attendance.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.present").value(true));
    }

    @Test
    void shouldCreateAttendance() throws Exception {
        AttendanceDTO newAttendance = new AttendanceDTO(null, LocalDateTime.now(), false, student.getId(), lesson.getId());

        mockMvc.perform(post("/api/attendances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAttendance)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.present").value(false)).andExpect(jsonPath("$.lessonId").exists()).andExpect(jsonPath("$.studentId").exists());
    }

    @Test
    void shouldUpdateAttendance() throws Exception {
        attendance.setPresent(false);
        AttendanceDTO dto = mapper.toDTO(attendance);
        mockMvc.perform(put("/api/attendances/" + attendance.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.present").value(false)).andExpect(jsonPath("$.lessonId").exists()).andExpect(jsonPath("$.studentId").exists());
    }

    @Test
    void shouldDeleteAttendance() throws Exception {
        mockMvc.perform(delete("/api/attendances/" + attendance.getId()))
                .andExpect(status().isNoContent());
    }

}
