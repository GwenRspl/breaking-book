package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.RoleName;
import com.projects.breakingbook.persistence.entity.User;
import com.projects.breakingbook.persistence.entity.Wishlist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistMapper implements RowMapper<Wishlist> {
    @Override
    public Wishlist mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = User.builder()
                .id(resultSet.getLong("reader_id"))
                .username(resultSet.getString("reader_username"))
                .avatar(resultSet.getString("reader_avatar"))
                .email(resultSet.getString("reader_email"))
                .password(resultSet.getString("reader_password"))
                .build();
        String role = (resultSet.getString("reader_role"));
        user.setRole(RoleName.valueOf(role));

        return Wishlist.builder()
                .id(resultSet.getLong("wishlist_id"))
                .name(resultSet.getString("wishlist_name"))
                .user(user)
                .build();
    }
}
