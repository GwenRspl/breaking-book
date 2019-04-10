package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.User;
import com.projects.breakingbook.persistence.entity.mapper.UserMapper;
import com.projects.breakingbook.persistence.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final static String INSERT_SQL = "insert into reader(reader_username, reader_email, reader_password, reader_role) values (?, ?, ?, ?)";
    private final static String SELECT_ALL_SQL = "select reader_id, reader_username, reader_email, reader_password, reader_role from reader";
    private final static String SELECT_BY_ID_SQL = "select reader_id, reader_username, reader_email, reader_password, reader_role from reader where reader_id = ?";
    private final static String DELETE_BY_ID_SQL = "delete from reader where reader_id = ?";
    private final static String DELETE_ALL_SQL = "delete from reader";
    private final static String UPDATE_BY_PUT_SQL = "update reader set reader_username = ?, reader_email = ?, reader_password = ?  where reader_id = ?";
    private final static String SELECT_ALL_USERNAMES_SQL = "select reader_username from reader";
    private final static String SELECT_ALL_EMAILS_SQL = "select reader_email from reader";

    @Override
    public void createUser(User user) {
        jdbcTemplate.update(INSERT_SQL, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole().toString());
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new UserMapper());
    }

    @Override
    public List<String> getUsernames() { return jdbcTemplate.queryForList(SELECT_ALL_USERNAMES_SQL, String.class); }

    @Override
    public List<String> getEmails() { return jdbcTemplate.queryForList(SELECT_ALL_EMAILS_SQL, String.class);}

    @Override
    public User updateUser(Long id, User user) {
        try {
            final Optional<User> optUser = findUserById(id);
            if (!optUser.isPresent()) {
                return null;
            }
            User oldUser = optUser.get();
            if (user.getUsername() == null) {
                user.setUsername(oldUser.getUsername());
            }
            if (user.getEmail() == null) {
                user.setEmail(oldUser.getEmail());
            }
            if (user.getPassword() == null) {
                user.setPassword(oldUser.getPassword());
            }
            jdbcTemplate.update(UPDATE_BY_PUT_SQL, user.getUsername(), user.getEmail(), user.getPassword(), id);
            return user;
        } catch (Exception e) {
            logger.error("Update! -> Message: {} ", e);
            return null;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            jdbcTemplate.update(DELETE_BY_ID_SQL, id);
            return true;
        } catch (Exception e) {
            logger.error("Update user failed! -> Message: {} ", e);
            return false;
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return getUsers().stream()
                .filter(opt -> opt.getUsername().trim().toLowerCase().contains(username.trim().toLowerCase()))
                .findFirst();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return getUsers().stream()
                .filter(optUser -> optUser.getId().equals(id))
                .findFirst();
    }

    @Override
    public Boolean existsByUsername(String username) {
        List<User> users = getUsers().stream()
                .filter(user -> user.getUsername().trim().toLowerCase().contains(username.trim().toLowerCase()))
                .collect(Collectors.toList());
        return users.size() != 0;
    }

    @Override
    public Boolean existsByEmail(String email) {
        List<User> users = getUsers().stream()
                .filter(user -> user.getEmail().trim().toLowerCase().contains(email.trim().toLowerCase()))
                .collect(Collectors.toList());
        return users.size() != 0;
    }
}
