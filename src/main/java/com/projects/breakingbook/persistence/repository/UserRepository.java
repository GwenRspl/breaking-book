package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    void createUser(User user);

    List<User> getUsers();

    List<String> getUsernames();

    List<String> getEmails();

    User updateUser(Long id, User user);

    boolean deleteUser(int id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(Long id);

    Boolean existsByUsername(String username);
        Boolean existsByEmail(String email);
}
