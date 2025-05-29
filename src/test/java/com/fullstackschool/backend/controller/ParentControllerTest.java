package com.fullstackschool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackschool.backend.DTO.ParentDTO;
import com.fullstackschool.backend.entity.Parent;
import com.fullstackschool.backend.mapper.ParentMapper;
import com.fullstackschool.backend.mapper.StudentMapper;
import com.fullstackschool.backend.repository.ParentRepository;
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
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ParentControllerTest {

    @Autowired private ParentMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private ParentRepository repository;
    @Autowired private ObjectMapper objectMapper;

    private Parent parent;

    @BeforeEach
    void setup() {
        parent = new Parent();
        parent.setId("parent1");
        parent.setUsername("parent1");
        parent.setName("Jane");
        parent.setSurname("Doe");
        parent.setPhone("123456789");
        parent.setAddress("123 Street");
        parent.setEmail("jane.doe@example.com");
        parent.setCreatedAt(LocalDateTime.now());
        parent.setStudents(Collections.emptyList());

        repository.saveAndFlush(parent);
    }

    @Test
    void shouldReturnAllParents() throws Exception {
        mockMvc.perform(get("/api/parents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnParentById() throws Exception {
        mockMvc.perform(get("/api/parents/parent1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("parent1"));
    }

    @Test
    void shouldCreateParent() throws Exception {
        ParentDTO newParent = new ParentDTO("parent2", "parent2", "Tom", "Smith", "tom@example.com",
                "456 Avenue", "987654321", LocalDateTime.now(), Collections.emptyList());

        mockMvc.perform(post("/api/parents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newParent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("parent2"));
    }

    @Test
    void shouldUpdateParent() throws Exception {
        parent.setName("UpdatedJane");
        ParentDTO dto = mapper.toDTO(parent);
        mockMvc.perform(put("/api/parents/parent1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedJane"));
    }

    @Test
    void shouldDeleteParent() throws Exception {
        mockMvc.perform(delete("/api/parents/parent1"))
                .andExpect(status().isNoContent());
    }
}
