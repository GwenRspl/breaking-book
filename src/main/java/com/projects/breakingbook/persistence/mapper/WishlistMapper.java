package com.projects.breakingbook.persistence.mapper;

import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.entity.Wishlist;
import com.projects.breakingbook.persistence.mapper.utils.MapperUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistMapper implements RowMapper<Wishlist> {
    @Override
    public Wishlist mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final User user = MapperUtils.generateUserFromResultSet(resultSet);

        return Wishlist.builder()
                .id(resultSet.getLong("wishlist_id"))
                .name(resultSet.getString("wishlist_name"))
                .user(user)
                .build();
    }
}
