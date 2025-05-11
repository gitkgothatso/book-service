package com.itstudio.bookservice.book_service.web;

import com.itstudio.bookservice.book_service.application.dto.BookDTO;
import com.itstudio.bookservice.book_service.domain.Book;
import com.itstudio.bookservice.book_service.ports.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookResource {

  private final BookService bookService;

  public BookResource(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public List<Book> all() {
    return bookService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> get(@PathVariable Long id) {
    return bookService.getById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Book create(@Validated @RequestBody BookDTO book) {
    return bookService.create(book);
  }

  @PutMapping("/{id}")
  public Book update(@PathVariable Long id, @Validated @RequestBody BookDTO book) {
    return bookService.update(id, book);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    bookService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
