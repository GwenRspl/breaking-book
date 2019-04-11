package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.User;
import com.projects.breakingbook.persistence.entity.mapper.UserMapExtractor;
import com.projects.breakingbook.persistence.entity.mapper.UserMapper;
import com.projects.breakingbook.persistence.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO reader(reader_username, reader_avatar, reader_email, reader_password, reader_role) VALUES (?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM reader";
    private final String SELECT_BY_ID = "SELECT * FROM reader WHERE reader_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM reader WHERE reader_id = ?";
    private final String DELETE_ALL = "DELETE FROM reader";
    private final String UPDATE = "UPDATE reader SET reader_username = ?, reader_avatar = ?, reader_email = ?, reader_password = ? WHERE reader_id = ?";

    private final String SELECT_JOIN  = "SELECT * FROM reader " +
            "INNER JOIN book ON book.book_reader = reader.reader_id " +
            "INNER JOIN friend ON book.book_friend = friend.friend_id;";

    private final String SELECT_JOIN_BY_ID = "SELECT * FROM reader " +
            "INNER JOIN book ON book.book_reader = reader.reader_id " +
            "INNER JOIN friend ON book.book_friend = friend.friend_id " +
            "WHERE reader.reader_id = ?;";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = this.jdbcTemplate.query(SELECT_ALL, new UserMapper());
        Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new UserMapExtractor());
        for (User user : users) {
            user.setBooks(booksMap.get(user.getId()));
        }
        return users;
    }

    @Override
    public boolean createUser(User user) {
        int result = this.jdbcTemplate.update(INSERT, user.getUsername(), user.getAvatar(), user.getEmail(), user.getPassword(), user.getRole().getRoleNameString());
        return result != 0;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return findAllUsers().stream()
                .filter(opt -> opt.getUsername().trim().toLowerCase().contains(username.trim().toLowerCase()))
                .findFirst();
    }

    @Override
    public User findUserById(Long id) {
        User user = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new UserMapper());
        Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new Object [] {id}, new UserMapExtractor());
        user.setBooks(booksMap.get(user.getId()));
        return user;
    }

    @Override
    public boolean deleteUserById(Long id) {
        int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllUsers() {
        int result = this.jdbcTemplate.update(DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateUser(Long id, User user) {
        int result = this.jdbcTemplate.update(UPDATE, user.getUsername(), user.getAvatar(), user.getEmail(), user.getPassword(), id);
        return result != 0;
    }

    @Override
    public Boolean existsByUsername(String username) {
        List<User> users = findAllUsers().stream()
                .filter(user -> user.getUsername().trim().toLowerCase().contains(username.trim().toLowerCase()))
                .collect(Collectors.toList());
        return users.size() != 0;
    }

    @Override
    public Boolean existsByEmail(String email) {
        List<User> users = findAllUsers().stream()
                .filter(user -> user.getEmail().trim().toLowerCase().contains(email.trim().toLowerCase()))
                .collect(Collectors.toList());
        return users.size() != 0;
    }
}
