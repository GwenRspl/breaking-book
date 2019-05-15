package com.projects.breakingbook.business.service;

import com.projects.breakingbook.persistence.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAll(final Long userId);

    Optional<Book> getOne(final Long id);

    Long create(final Book book);

    boolean update(final Long id, final Book book);

    boolean delete(final Long id);

    boolean deleteAll();

    boolean toggleOwned(Long id);

    boolean updateFriend(Long bookId, Long friendId);
}
