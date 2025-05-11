package com.itstudio.bookservice.book_service.application;

import com.itstudio.bookservice.book_service.domain.Book;
import com.itstudio.bookservice.book_service.ports.BookRepository;
import com.itstudio.bookservice.book_service.ports.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> getAll() {
    return bookRepository.findAll();
  }

  @Override
  public Optional<Book> getById(Long id) {
    return bookRepository.findById(id);
  }

  @Override
  public Book create(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public Book update(Long id, Book book) {
    //TODO book.setId(id);
    return bookRepository.save(book);
  }

  @Override
  public void delete(Long id) {
    bookRepository.deleteById(id);
  }
}
