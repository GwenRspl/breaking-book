package com.projects.breakingbook.persistence.mapper;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.utils.MapperUtils;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistMapExtractor implements ResultSetExtractor<Map<Long, List<Book>>> {
    @Override
    public Map<Long, List<Book>> extractData(final ResultSet resultSet) throws SQLException {
        Map<Long, List<Book>> booksMap = new HashMap<>();

        while (resultSet.next()) {
            if (resultSet.getLong("book_wishlist_book_id") == 0) {
                return booksMap;
            }

            final Long wishlistId = resultSet.getLong("wishlist_id");
            final User user = MapperUtils.generateUserFromResultSet(resultSet);
            final Friend friend = MapperUtils.generateFriendFromResultSet(resultSet, user, Arrays.asList("book_friend", "friend_name", "friend_avatar"));
            final Book book = MapperUtils.generateBookFromResultSet(resultSet, friend, user, "book_wishlist_book_id");
            booksMap = MapperUtils.generateBooksMap(booksMap, wishlistId, book);
        }
        return booksMap;
    }
}
