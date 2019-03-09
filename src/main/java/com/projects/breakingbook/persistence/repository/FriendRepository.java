package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Friend;

import java.util.List;

public interface FriendRepository {

    List<Friend> findAllFriends();
    void createFriend(Friend friend);
    Friend findFriendById(Long id);
    void deleteFriendById(Long id);
    void deleteAllFriends();
    void updateFriend(Long id, Friend friend);
}
