package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.business.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAllBooks(Long userId);

    Long createBook(Book book);

    Optional<Book> findBookById(Long id);

    boolean deleteBookById(Long id);

    boolean deleteAllBooks();

    boolean updateBook(Long id, Book book);

    boolean toggleOwned(Long id);

    boolean updateFriend(Long bookId, Long friendId);
}
