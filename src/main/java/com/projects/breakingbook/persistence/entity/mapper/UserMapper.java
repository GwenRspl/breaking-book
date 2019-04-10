package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.RoleName;
import com.projects.breakingbook.persistence.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("reader_id"));
        user.setUsername(rs.getString("reader_username"));
        user.setEmail(rs.getString("reader_email"));
        user.setPassword(rs.getString("reader_password"));
        String role = (rs.getString("reader_role"));
        user.setRole(RoleName.valueOf(role));
        return user;
    }
}
