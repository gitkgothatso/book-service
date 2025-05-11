package com.itstudio.bookservice.book_service.infrastructure.repository;

import com.itstudio.bookservice.book_service.domain.Book;
import com.itstudio.bookservice.book_service.ports.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

  private final JpaBookRepository jpaBookRepository;

  public BookRepositoryImpl(JpaBookRepository jpaBookRepository) {
    this.jpaBookRepository = jpaBookRepository;
  }

  private BookEntity toEntity(Book book) {
    BookEntity entity = new BookEntity();
    entity.setId(book.id());
    entity.setTitle(book.title());
    entity.setAuthor(book.author());
    entity.setPublishedDate(book.publishedDate());
    entity.setIsbn(book.isbn());
    return entity;
  }

  private Book toDomain(BookEntity entity) {
    Book book = new Book(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getPublishedDate(), entity.getIsbn());
    return book;
  }

  @Override
  public List<Book> findAll() {
    return jpaBookRepository.findAll().stream().map(this::toDomain).toList();
  }

  @Override
  public Optional<Book> findById(Long id) {
    return jpaBookRepository.findById(id).map(this::toDomain);
  }

  @Override
  public Book save(Book book) {
    return toDomain(jpaBookRepository.save(toEntity(book)));
  }

  @Override
  public void deleteById(Long id) {
    jpaBookRepository.deleteById(id);
  }
}
