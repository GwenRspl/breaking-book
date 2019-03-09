package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAllBooks();
    void createBook(Book book);
    Book findBookById(Long id);
    void deleteBookById(Long id);
    void deleteAllBooks();
    void updateBook(Long id, Book book);
}
