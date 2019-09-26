package com.projects.breakingbook.business.service;

import com.projects.breakingbook.business.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    Optional<User> getOne(final Long id);

    boolean create(final User user);

    Optional<User> findUserByUsername(String username);

    boolean update(final Long id, final User user);

    boolean delete(final Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
