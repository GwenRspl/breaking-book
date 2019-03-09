package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.mapper.FriendMapper;
import com.projects.breakingbook.persistence.repository.FriendRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class FriendRepositoryImpl implements FriendRepository {

    // TODO : ambiguous columns name
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO friend(friend_name, friend_avatar, friend_reader) VALUES (?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM friend INNER JOIN reader r ON friend.friend_reader = r.reader_id";
    private final String SELECT_BY_ID = "SELECT * FROM friend INNER JOIN reader r ON friend.friend_reader = r.reader_id " +
            "WHERE friend_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM friend WHERE friend_id = ?";
    private final String DELETE_ALL = "DELETE FROM friend";
    private final String UPDATE = "UPDATE friend SET friend_name = ?, friend_avatar = ?, friend_reader = ? WHERE " +
            "friend_id = ?";

    public FriendRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Friend> findAllFriends() {
        return this.jdbcTemplate.query(SELECT_ALL, new FriendMapper());
    }

    @Override
    public void createFriend(Friend friend) {
        this.jdbcTemplate.update(INSERT, friend.getName(), friend.getAvatar(), friend.getReader());
    }

    @Override
    public Friend findFriendById(Long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new FriendMapper());
    }

    @Override
    public void deleteFriendById(Long id) {
        this.jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void deleteAllFriends() {
        this.jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public void updateFriend(Long id, Friend friend) {
        this.jdbcTemplate.update(UPDATE, friend.getName(), friend.getAvatar(), friend.getReader(), id);
    }
}
