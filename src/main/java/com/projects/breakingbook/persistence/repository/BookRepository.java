package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAllBooks();
    boolean createBook(Book book);
    Book findBookById(Long id);
    boolean deleteBookById(Long id);
    boolean deleteAllBooks();
    boolean updateBook(Long id, Book book);
}
