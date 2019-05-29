package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.mapper.FriendMapExtractor;
import com.projects.breakingbook.persistence.entity.mapper.FriendMapper;
import com.projects.breakingbook.persistence.repository.FriendRepository;
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

    private final JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO friend(friend_name, friend_avatar, friend_breaking_book_user) VALUES (?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM friend INNER JOIN breaking_book_user r ON friend.friend_breaking_book_user = r.breaking_book_user_id WHERE friend.friend_breaking_book_user = ?;";
    private final String SELECT_BY_ID = "SELECT * FROM friend INNER JOIN breaking_book_user r ON friend.friend_breaking_book_user = r.breaking_book_user_id " +
            "WHERE friend_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM friend WHERE friend_id = ?";
    private final String DELETE_ALL = "DELETE FROM friend";
    private final String UPDATE = "UPDATE friend SET friend_name = ?, friend_avatar = ?, friend_breaking_book_user = ? WHERE " +
            "friend_id = ?";

    private final String SELECT_JOIN = "SELECT * FROM friend " +
            "INNER JOIN book_friend ON friend.friend_id = book_friend.book_friend_friend_id " +
            "INNER JOIN book ON book.book_id = book_friend.book_friend_book_id " +
            "INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id ";

    private final String SELECT_JOIN_BY_ID = "SELECT * FROM friend " +
            "INNER JOIN book_friend ON friend.friend_id = book_friend.book_friend_friend_id " +
            "INNER JOIN book ON book.book_id = book_friend.book_friend_book_id " +
            "INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id " +
            "WHERE friend.friend_id = ?;";

    private final String SELECT_BOOK_BY_FRIEND_ID = "SELECT book_id FROM book WHERE book_friend = ?";

    public FriendRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Friend> findAllFriends(final Long userId) {
        final List<Friend> friends = this.jdbcTemplate.query(this.SELECT_ALL, new Object[]{userId}, new FriendMapper());
        final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(this.SELECT_JOIN, new FriendMapExtractor());
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
                    .prepareStatement(this.INSERT, Statement.RETURN_GENERATED_KEYS);
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
            final Friend friend = this.jdbcTemplate.queryForObject(this.SELECT_BY_ID, new Object[]{id}, new FriendMapper());
            final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(this.SELECT_JOIN_BY_ID, new Object[]{id}, new FriendMapExtractor());
            friend.setHistory(booksMap.get(friend.getId()));
            return Optional.of(friend);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteFriendById(final Long id) {
        final int result = this.jdbcTemplate.update(this.DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllFriends() {
        final int result = this.jdbcTemplate.update(this.DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateFriend(final Long id, final Friend friend) {
        final int result = this.jdbcTemplate.update(this.UPDATE, friend.getName(), friend.getAvatar(), friend.getUser().getId(), id);
        return result != 0;
    }

    @Override
    public Long getBorrowedBook(final Long friendId) {
        try {
            return this.jdbcTemplate.queryForObject(this.SELECT_BOOK_BY_FRIEND_ID, new Object[]{friendId}, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            System.out.println(e);
            return null;
        }
    }
}
