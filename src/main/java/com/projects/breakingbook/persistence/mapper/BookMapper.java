package com.projects.breakingbook.persistence.mapper;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.persistence.mapper.utils.MapperUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final User user = MapperUtils.generateUserFromResultSet(resultSet);
        final Friend friend = MapperUtils.generateFriendFromResultSet(resultSet, user, Arrays.asList("book_friend", "friend_name", "friend_avatar"));
        return MapperUtils.generateBookFromResultSet(resultSet, friend, user, "book_id");
    }
}
