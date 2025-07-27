package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.GradeDTO;
import com.fullstackschool.backend.config.NoSecurityConfig;
import com.fullstackschool.backend.entity.Grade;
import com.fullstackschool.backend.mapper.GradeMapper;
import com.fullstackschool.backend.repository.GradeRepository;
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
public class GradeControllerTest {

    @Autowired private GradeMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private GradeRepository repository;
    @Autowired private ObjectMapper objectMapper;

    private Grade grade;

    @BeforeEach
    void setup() {
        grade = new Grade(null, 1, List.of(),List.of());

        repository.saveAndFlush(grade);
    }

    @Test
    void shouldReturnAllGrades() throws Exception {
        mockMvc.perform(get("/api/grades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnGradeById() throws Exception {
        mockMvc.perform(get("/api/grades/" + grade.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("1"));
    }

    @Test
    void shouldCreateGrade() throws Exception {
        GradeDTO newGrade = new GradeDTO(null, 6, List.of(),List.of());

        mockMvc.perform(post("/api/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGrade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value(6));
    }

    @Test
    void shouldUpdateGrade() throws Exception {
        grade.setLevel(5);
        GradeDTO dto = mapper.toDTO(grade);
        mockMvc.perform(put("/api/grades/" + grade.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value(5));
    }

    @Test
    void shouldDeleteGrade() throws Exception {
        mockMvc.perform(delete("/api/grades/" + grade.getId()))
                .andExpect(status().isNoContent());
    }
}
