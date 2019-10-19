package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.persistence.mapper.FriendMapExtractor;
import com.projects.breakingbook.persistence.mapper.FriendMapper;
import com.projects.breakingbook.persistence.repository.FriendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class FriendRepositoryImpl implements FriendRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendRepository.class);
    private static final String INSERT = new StringBuilder().append("INSERT INTO friend")
            .append("(friend_name, friend_avatar, friend_breaking_book_user)")
            .append(" VALUES ")
            .append("(?, ?, ?)")
            .toString();
    private static final String SELECT_ALL = new StringBuilder()
            .append("SELECT friend_id, friend_name, friend_avatar, friend_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM friend ")
            .append("INNER JOIN breaking_book_user r ")
            .append("ON friend.friend_breaking_book_user = r.breaking_book_user_id ")
            .append("WHERE friend.friend_breaking_book_user = ?;")
            .toString();
    private static final String SELECT_BY_ID = new StringBuilder()
            .append("SELECT friend_id, friend_name, friend_avatar, friend_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM friend ")
            .append("INNER JOIN breaking_book_user r ON friend.friend_breaking_book_user = r.breaking_book_user_id ")
            .append("WHERE friend_id = ?")
            .toString();
    private static final String DELETE_BY_ID = new StringBuilder()
            .append("DELETE FROM friend ")
            .append("WHERE friend_id = ?")
            .toString();
    private static final String UPDATE = new StringBuilder()
            .append("UPDATE friend ")
            .append("SET friend_name = ?, friend_avatar = ?, friend_breaking_book_user = ? ")
            .append("WHERE friend_id = ?")
            .toString();
    private static final String SELECT_JOIN = new StringBuilder()
            .append("SELECT friend_id, friend_name, friend_avatar, friend_breaking_book_user, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("book_friend_friend_id, book_friend_book_id ")
            .append("FROM friend ")
            .append("INNER JOIN book_friend ON friend.friend_id = book_friend.book_friend_friend_id ")
            .append("INNER JOIN book ON book.book_id = book_friend.book_friend_book_id ")
            .append("INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id ")
            .toString();
    private static final String SELECT_JOIN_BY_ID = new StringBuilder()
            .append("SELECT friend_id, friend_name, friend_avatar, friend_breaking_book_user, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("book_friend_friend_id, book_friend_book_id ")
            .append("FROM friend ")
            .append("INNER JOIN book_friend ON friend.friend_id = book_friend.book_friend_friend_id ")
            .append("INNER JOIN book ON book.book_id = book_friend.book_friend_book_id ")
            .append("INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id ")
            .append("WHERE friend.friend_id = ?;")
            .toString();
    private static final String SELECT_BOOK_BY_FRIEND_ID = "SELECT book_id FROM book WHERE book_friend = ?;";
    private static final String INSERT_BOOK_TO_HISTORY = new StringBuilder()
            .append("INSERT INTO book_friend")
            .append("(book_friend_book_id, book_friend_friend_id) ")
            .append("VALUES (?, ?)")
            .toString();
    private final JdbcTemplate jdbcTemplate;

    public FriendRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Friend> findAllFriends(final Long userId) {
        final List<Friend> friends = this.jdbcTemplate.query(SELECT_ALL, new Object[]{userId}, new FriendMapper());
        final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new FriendMapExtractor());
        for (final Friend friend : friends) {
            friend.setHistory(booksMap.get(friend.getId()));
        }
        return friends;
    }

    @Override
    public Long createFriend(final Friend friend) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection
                    .prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, friend.getName());
            ps.setString(2, friend.getAvatar());
            ps.setLong(3, friend.getUser().getId());
            return ps;
        }, keyHolder);

        final Long newId;
        if (keyHolder.getKeys().size() > 1) {
            newId = ((Integer) keyHolder.getKeys().get("friend_id")).longValue();
        } else {
            newId = keyHolder.getKey().longValue();
        }
        return newId;
    }

    @Override
    public Optional<Friend> findFriendById(final Long id) {
        try {
            final Friend friend = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new FriendMapper());
            final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new Object[]{id}, new FriendMapExtractor());
            friend.setHistory(booksMap.get(friend.getId()));
            return Optional.of(friend);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteFriendById(final Long id) {
        final int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean updateFriend(final Long id, final Friend friend) {
        final int result = this.jdbcTemplate.update(UPDATE, friend.getName(), friend.getAvatar(), friend.getUser().getId(), id);
        return result != 0;
    }

    @Override
    public List<Long> getBorrowedBook(final Long friendId) {
        try {
            return this.jdbcTemplate.query(SELECT_BOOK_BY_FRIEND_ID, new Object[]{friendId}, (resultSet, i) -> resultSet.getLong("book_id"));
        } catch (final EmptyResultDataAccessException e) {
            LOGGER.error("Cannot get borrowed books", e);
            return null;
        }
    }

    @Override
    public boolean addBookToHistory(final Long bookId, final Long friendId) {
        final int result = this.jdbcTemplate.update(INSERT_BOOK_TO_HISTORY, bookId, friendId);
        return result != 0;
    }
}
