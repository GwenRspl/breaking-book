package com.projects.breakingbook.persistence.mapper;

import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.utils.MapperUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class FriendMapper implements RowMapper<Friend> {

    @Override
    public Friend mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final User user = MapperUtils.generateUserFromResultSet(resultSet);
        return MapperUtils.generateFriendFromResultSet(resultSet, user, Arrays.asList("friend_id", "friend_name", "friend_avatar"));
    }
}
