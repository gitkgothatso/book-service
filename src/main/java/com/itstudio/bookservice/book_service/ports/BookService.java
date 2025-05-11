package com.itstudio.bookservice.book_service.ports;

import com.itstudio.bookservice.book_service.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
  List<Book> getAll();
  Optional<Book> getById(Long id);
  Book create(Book book);
  Book update(Long id, Book book);
  void delete(Long id);
}
