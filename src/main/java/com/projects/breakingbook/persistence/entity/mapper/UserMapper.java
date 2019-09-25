package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.User;
import com.projects.breakingbook.persistence.entity.RoleName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = User.builder()
                .id(resultSet.getLong("breaking_book_user_id"))
                .username(resultSet.getString("breaking_book_user_username"))
                .avatar(resultSet.getString("breaking_book_user_avatar"))
                .email(resultSet.getString("breaking_book_user_email"))
                .password(resultSet.getString("breaking_book_user_password"))
                .build();
        String role = (resultSet.getString("breaking_book_user_role"));
        user.setRole(RoleName.valueOf(role));
        return user;

    }
}
