package com.projects.breakingbook.business.service;

import com.projects.breakingbook.persistence.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();
    Book getOne(final Long id);
    boolean create(final Book book);
    boolean update(final Long id, final Book book);
    boolean delete(final Long id);
    boolean deleteAll();
}
