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
                .id(resultSet.getLong("reader_id"))
                .build();

        return Friend.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .avatar(resultSet.getString("avatar"))
                .reader(reader)
                .build();
    }
}
