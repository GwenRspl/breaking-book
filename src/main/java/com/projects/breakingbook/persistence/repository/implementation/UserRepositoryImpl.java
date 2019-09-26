package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.persistence.mapper.UserMapExtractor;
import com.projects.breakingbook.persistence.mapper.UserMapper;
import com.projects.breakingbook.persistence.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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

    private static final String INSERT = new StringBuilder()
            .append("INSERT INTO breaking_book_user")
            .append("(breaking_book_user_username, breaking_book_user_avatar, breaking_book_user_email, ")
            .append("breaking_book_user_password, breaking_book_user_role)")
            .append(" VALUES ")
            .append("(?, ?, ?, ?, ?)")
            .toString();
    private static final String SELECT_ALL = new StringBuilder()
            .append("SELECT ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM breaking_book_user")
            .toString();
    private static final String SELECT_BY_ID = new StringBuilder()
            .append("SELECT ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar,")
            .append(" breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM breaking_book_user ")
            .append("WHERE breaking_book_user_id = ?")
            .toString();
    private static final String DELETE_BY_ID = "DELETE FROM breaking_book_user WHERE breaking_book_user_id = ?";
    private static final String UPDATE = new StringBuilder()
            .append("UPDATE breaking_book_user ")
            .append("SET breaking_book_user_username = ?, breaking_book_user_avatar = ?, ")
            .append("breaking_book_user_email = ?, breaking_book_user_password = ? ")
            .append("WHERE breaking_book_user_id = ?")
            .toString();
    private static final String SELECT_JOIN = new StringBuilder()
            .append("SELECT ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user ")
            .append("FROM breaking_book_user ")
            .append("LEFT JOIN book ON book.book_breaking_book_user = breaking_book_user.breaking_book_user_id ")
            .append("LEFT JOIN friend ON book.book_friend = friend.friend_id;")
            .toString();
    private static final String SELECT_JOIN_BY_ID = new StringBuilder()
            .append("SELECT ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user ")
            .append("FROM breaking_book_user ")
            .append("LEFT JOIN book ON book.book_breaking_book_user = breaking_book_user.breaking_book_user_id ")
            .append("LEFT JOIN friend ON book.book_friend = friend.friend_id ")
            .append("WHERE breaking_book_user.breaking_book_user_id = ?;")
            .toString();
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllUsers() {
        final List<User> users = this.jdbcTemplate.query(SELECT_ALL, new UserMapper());
        final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new UserMapExtractor());
        if (booksMap != null) {
            for (final User user : users) {
                user.setBooks(booksMap.get(user.getId()));
            }
        }
        return users;
    }

    @Override
    public boolean createUser(final User user) {
        final int result = this.jdbcTemplate.update(INSERT, user.getUsername(), user.getAvatar(), user.getEmail(), user.getPassword(), user.getRole().getRoleNameString());
        return result != 0;
    }

    @Override
    public Optional<User> findUserByUsername(final String username) {
        return this.findAllUsers().stream()
                .filter(opt -> opt.getUsername().trim().toLowerCase().contains(username.trim().toLowerCase()))
                .findFirst();
    }

    @Override
    public Optional<User> findUserById(final Long id) {
        try {
            final User user = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new UserMapper());
            final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new Object[]{id}, new UserMapExtractor());
            user.setBooks(booksMap.get(user.getId()));
            return Optional.of(user);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteUserById(final Long id) {
        final int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean updateUser(final Long id, final User user) {
        final int result = this.jdbcTemplate.update(UPDATE, user.getUsername(), user.getAvatar(), user.getEmail(), user.getPassword(), id);
        return result != 0;
    }

    @Override
    public Boolean existsByUsername(final String username) {
        final List<User> users = this.findAllUsers().stream()
                .filter(user -> user.getUsername().trim().toLowerCase().contains(username.trim().toLowerCase()))
                .collect(Collectors.toList());
        return !users.isEmpty();
    }

    @Override
    public Boolean existsByEmail(final String email) {
        final List<User> users = this.findAllUsers().stream()
                .filter(user -> user.getEmail().trim().toLowerCase().contains(email.trim().toLowerCase()))
                .collect(Collectors.toList());
        return !users.isEmpty();
    }
}
