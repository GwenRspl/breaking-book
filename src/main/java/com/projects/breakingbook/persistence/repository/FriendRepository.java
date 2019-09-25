package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {

    List<Friend> findAllFriends();
    boolean createFriend(Friend friend);
    Optional<Friend> findFriendById(Long id);
    boolean deleteFriendById(Long id);
    boolean deleteAllFriends();
    boolean updateFriend(Long id, Friend friend);
    Long getBorrowedBook(Long friendId);
}
