package com.itstudio.bookservice.book_service.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itstudio.bookservice.book_service.application.dto.BookDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// Optional: If using assertions


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookResourceIT {

    private static Long createdBookId;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("pass");

    @DynamicPropertySource
    private static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void shouldCreateBook() throws Exception {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", LocalDate.of(2008, 8, 1), "9780132350884");

        var result = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andReturn();

        var json = result.getResponse().getContentAsString();
        createdBookId = objectMapper.readTree(json).get("id").asLong();
    }

    @Test
    @Order(2)
    void shouldGetBookById() throws Exception {
        mockMvc.perform(get("/api/books/{id}", createdBookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @Order(3)
    void shouldUpdateBook() throws Exception {
        BookDTO updatedBook = new BookDTO("Clean Code (Updated)", "Uncle Bob", LocalDate.of(2008, 8, 1), "9780132350884");

        mockMvc.perform(put("/api/books/{id}", createdBookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code (Updated)"));
    }

    @Test
    @Order(4)
    void shouldGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @Order(5)
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", createdBookId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books/{id}", createdBookId))
                .andExpect(status().isNotFound());
    }

}
