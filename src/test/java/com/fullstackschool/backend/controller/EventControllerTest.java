package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.EventDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.EventMapper;
import com.fullstackschool.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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
@Import(NoSecurityConfig.class)
@Transactional
public class EventControllerTest {

    @Autowired
    private EventMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private EventRepository repository;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private ObjectMapper objectMapper;

    private Event event;
    private SchoolClass schoolClass;

    @BeforeEach
    void setup() {
        Teacher teacher = new Teacher("teacher1", "teacher1", "John", "Doe", "john@mail.com", "123456", "123 rue", "img.png", "O+", UserSex.MALE, LocalDateTime.now(), LocalDateTime.of(1990, 1, 1, 0, 0), List.of(), List.of(), List.of());
        teacherRepository.saveAndFlush(teacher);


        Subject subject = new Subject(null, "Mathematics", List.of(),List.of());
        subjectRepository.saveAndFlush(subject);

        Grade grade = new Grade(null, 1, List.of(), List.of());
        gradeRepository.saveAndFlush(grade);

        schoolClass = new SchoolClass(null, "Class A", 30, teacher, List.of(), List.of(), grade, List.of(), List.of());
        schoolClassRepository.saveAndFlush(schoolClass);

        Lesson lesson = new Lesson(null, "Algebra", Day.MONDAY, LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                subject, schoolClass, teacher, List.of(), List.of(), List.of());
        lessonRepository.saveAndFlush(lesson);

        event = new Event(null, "Event1", "event1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), schoolClass);

        repository.saveAndFlush(event);
    }

    @Test
    void shouldReturnAllEvents() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnEventById() throws Exception {
        mockMvc.perform(get("/api/events/" + event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Event1"));
    }

    @Test
    void shouldCreateEvent() throws Exception {
        EventDTO newEvent = new EventDTO(null, "Event2", "event2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), schoolClass.getId());

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Event2")).andExpect(jsonPath("$.classId").exists());
    }

    @Test
    void shouldUpdateEvent() throws Exception {
        event.setTitle("TEST");
        EventDTO dto = mapper.toDTO(event);
        mockMvc.perform(put("/api/events/" + event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("TEST")).andExpect(jsonPath("$.classId").exists());
    }

    @Test
    void shouldDeleteEvent() throws Exception {
        mockMvc.perform(delete("/api/events/" + event.getId()))
                .andExpect(status().isNoContent());
    }

}
