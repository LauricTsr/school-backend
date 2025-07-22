package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.SubjectDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.Subject;
import com.fullstackschool.backend.mapper.SubjectMapper;
import com.fullstackschool.backend.mapper.StudentMapper;
import com.fullstackschool.backend.repository.SubjectRepository;
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
class SubjectControllerTest {

    @Autowired private SubjectMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private SubjectRepository repository;
    @Autowired private ObjectMapper objectMapper;

    private Subject subject;

    @BeforeEach
    void setup() {
        subject = new Subject(null,"Subject1", List.of(), List.of());
        repository.saveAndFlush(subject);
    }

    @Test
    void shouldReturnAllSubjects() throws Exception {
        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnSubjectById() throws Exception {
        mockMvc.perform(get("/api/subjects/" + subject.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Subject1"));
    }

    @Test
    void shouldCreateSubject() throws Exception {
        SubjectDTO newSubject = new SubjectDTO(null, "Subject2",List.of(), List.of());

        mockMvc.perform(post("/api/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSubject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Subject2"));
    }

    @Test
    void shouldUpdateSubject() throws Exception {
        subject.setName("TEST");
        SubjectDTO dto = mapper.toDTO(subject);
        mockMvc.perform(put("/api/subjects/" + subject.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TEST"));
    }

    @Test
    void shouldDeleteSubject() throws Exception {
        mockMvc.perform(delete("/api/subjects/" + subject.getId()))
                .andExpect(status().isNoContent());
    }
}
