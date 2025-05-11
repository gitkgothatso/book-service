package com.itstudio.bookservice.book_service.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookRepository extends JpaRepository<BookEntity, Long> {}
