package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.AnnouncementDTO;
import com.fullstackschool.backend.entity.*;
import com.fullstackschool.backend.mapper.AnnouncementMapper;
import com.fullstackschool.backend.repository.AnnouncementRepository;
import com.fullstackschool.backend.repository.GradeRepository;
import com.fullstackschool.backend.repository.SchoolClassRepository;
import com.fullstackschool.backend.repository.TeacherRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AnnouncementControllerTest {

    @Autowired
    private AnnouncementMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private AnnouncementRepository repository;
    @Autowired private SchoolClassRepository schoolClassRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private ObjectMapper objectMapper;

    private Announcement announcement;
    private SchoolClass schoolClass;

    @BeforeEach
    void setup() {
        Teacher teacher = new Teacher("teacher1", "teacher1", "John", "Doe", "john@mail.com", "123456", "123 rue", "img.png", "O+", UserSex.MALE, LocalDateTime.now(), LocalDateTime.of(1990, 1, 1, 0, 0), List.of(), List.of(), List.of());
        teacherRepository.saveAndFlush(teacher);
        Grade grade = new Grade(null, 1, List.of(), List.of());
        gradeRepository.saveAndFlush(grade);


        schoolClass = new SchoolClass(null, "Class A", 30, teacher, List.of(), List.of(), grade, List.of(), List.of());
        schoolClassRepository.saveAndFlush(schoolClass);


        announcement = new Announcement(null, "Announcement1", "first one", LocalDateTime.now(),schoolClass);

        repository.saveAndFlush(announcement);
    }

    @Test
    void shouldReturnAllAnnouncements() throws Exception {
        mockMvc.perform(get("/api/announcements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnAnnouncementById() throws Exception {
        mockMvc.perform(get("/api/announcements/" + announcement.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Announcement1"));
    }

    @Test
    void shouldCreateAnnouncement() throws Exception {
        AnnouncementDTO newAnnouncement = new AnnouncementDTO(null, "Announcement2", "second one", LocalDateTime.now(), schoolClass.getId());

        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAnnouncement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Announcement2"));
    }

    @Test
    void shouldUpdateAnnouncement() throws Exception {
        announcement.setTitle("TEST");
        AnnouncementDTO dto = mapper.toDTO(announcement);
        mockMvc.perform(put("/api/announcements/" + announcement.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("TEST"));
    }

    @Test
    void shouldDeleteAnnouncement() throws Exception {
        mockMvc.perform(delete("/api/announcements/" + announcement.getId()))
                .andExpect(status().isNoContent());
    }
}
