package com.projects.breakingbook.business.service;

import com.projects.breakingbook.business.entity.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendService {

    List<Friend> getAll(Long userId);

    Optional<Friend> getOne(final Long id);

    Long create(final Friend friend);

    boolean update(final Long id, final Friend friend);

    boolean delete(final Long id);

    boolean deleteAll();

    Long getBorrowedBook(Long id);

    boolean addBookToHistory(Long friendId, Long bookId);
}
