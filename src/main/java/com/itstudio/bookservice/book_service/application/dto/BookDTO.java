package com.itstudio.bookservice.book_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record BookDTO(
  @NotBlank String title,
  @NotBlank String author,
  @NotNull LocalDate publishedDate,
  @Pattern(regexp = "\\d{10}|\\d{13}") String isbn
) {}
