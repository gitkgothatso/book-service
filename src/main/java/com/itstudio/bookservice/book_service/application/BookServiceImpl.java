package com.itstudio.bookservice.book_service.application;

import com.itstudio.bookservice.book_service.application.dto.BookDTO;
import com.itstudio.bookservice.book_service.domain.Book;
import com.itstudio.bookservice.book_service.ports.BookRepository;
import com.itstudio.bookservice.book_service.ports.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
  public Book create(BookDTO book) {

    Book newBook = new Book(null, book.title(), book.author(), book.publishedDate(), book.isbn());
    return bookRepository.save(newBook);
  }

  @Override
  public Book update(Long id, BookDTO book) {
    Optional<Book> updatedBook = bookRepository.findById(id);

    if(updatedBook.isPresent()){
      return bookRepository.save(new Book(id, book.title(), book.author(), book.publishedDate(), book.isbn()));
    };
    return bookRepository.save(new Book(null,book.title(), book.author(), book.publishedDate(), book.isbn()));
  }

  @Override
  public void delete(Long id) {
    bookRepository.deleteById(id);
  }
}
