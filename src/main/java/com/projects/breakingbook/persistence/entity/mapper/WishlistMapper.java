package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.Reader;
import com.projects.breakingbook.persistence.entity.Wishlist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistMapper implements RowMapper<Wishlist> {
    @Override
    public Wishlist mapRow(ResultSet resultSet, int i) throws SQLException {
        Reader reader = Reader.builder()
                .id(resultSet.getLong("wishlist_reader"))
                .build();

        return Wishlist.builder()
                .id(resultSet.getLong("wishlist_id"))
                .name(resultSet.getString("wishlist_name"))
                .reader(reader)
                .build();
    }
}
