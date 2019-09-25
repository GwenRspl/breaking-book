package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.business.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAllUsers();

    boolean createUser(User user);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(Long id);

    boolean deleteUserById(Long id);

    boolean deleteAllUsers();

    boolean updateUser(Long id, User user);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
