package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.business.entity.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {

    List<Friend> findAllFriends(Long userId);

    Long createFriend(Friend friend);

    Optional<Friend> findFriendById(Long id);

    boolean deleteFriendById(Long id);

    boolean updateFriend(Long id, Friend friend);

    List<Long> getBorrowedBook(Long friendId);

    boolean addBookToHistory(Long bookId, Long friendId);
}
