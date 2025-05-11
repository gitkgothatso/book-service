package com.itstudio.bookservice.book_service.domain;

import java.time.LocalDate;

public record Book( Long id,String title,String author,LocalDate publishedDate,
                   String isbn) {
}
