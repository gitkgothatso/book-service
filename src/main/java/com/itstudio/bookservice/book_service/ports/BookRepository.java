package com.itstudio.bookservice.book_service.ports;

import com.itstudio.bookservice.book_service.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
  List<Book> findAll();
  Optional<Book> findById(Long id);
  Book save(Book book);
  void deleteById(Long id);
}
