package com.projects.breakingbook.business.service;

import com.projects.breakingbook.persistence.entity.Friend;

import java.util.List;

public interface FriendService {

    List<Friend> getAll();
    Friend getOne(final Long id);
    boolean create(final Friend friend);
    boolean update(final Long id, final Friend friend);
    boolean delete(final Long id);
    boolean deleteAll();
}
