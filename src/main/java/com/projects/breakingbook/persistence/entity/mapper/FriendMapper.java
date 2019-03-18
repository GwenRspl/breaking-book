package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.Reader;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendMapper implements RowMapper<Friend> {
    @Override
    public Friend mapRow(ResultSet resultSet, int i) throws SQLException {
        Reader reader = Reader.builder()
                .id(resultSet.getLong("friend_reader"))
                .name(resultSet.getString("reader_name"))
                .avatar(resultSet.getString("reader_avatar"))
                .email(resultSet.getString("reader_email"))
                .password(resultSet.getString("reader_password"))
                .build();

        return Friend.builder()
                .id(resultSet.getLong("friend_id"))
                .name(resultSet.getString("friend_name"))
                .avatar(resultSet.getString("friend_avatar"))
                .reader(reader)
                .build();
    }
}
