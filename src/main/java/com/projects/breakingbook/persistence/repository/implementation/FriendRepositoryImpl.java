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
    public boolean createFriend(Friend friend) {
        int result = this.jdbcTemplate.update(INSERT, friend.getName(), friend.getAvatar(), friend.getReader().getId());
        return result != 0;
    }

    @Override
    public Friend findFriendById(Long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new FriendMapper());
    }

    @Override
    public boolean deleteFriendById(Long id) {
        int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllFriends() {
        int result = this.jdbcTemplate.update(DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateFriend(Long id, Friend friend) {
        int result = this.jdbcTemplate.update(UPDATE, friend.getName(), friend.getAvatar(), friend.getReader().getId(), id);
        return result != 0;
    }
}
