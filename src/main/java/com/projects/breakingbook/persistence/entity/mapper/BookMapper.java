package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = User.builder()
                .id(resultSet.getLong("breaking_book_user_id"))
                .username(resultSet.getString("breaking_book_user_username"))
                .avatar(resultSet.getString("breaking_book_user_avatar"))
                .email(resultSet.getString("breaking_book_user_email"))
                .password(resultSet.getString("breaking_book_user_password"))
                .build();
        String role = (resultSet.getString("breaking_book_user_role"));
        user.setRole(RoleName.valueOf(role));

        Friend friend = Friend.builder()
                .id(resultSet.getLong("book_friend"))
                .name(resultSet.getString("friend_name"))
                .avatar(resultSet.getString("friend_avatar"))
                .user(user)
                .build();

        String[] authorsArray = (String[]) resultSet.getArray("book_authors").getArray();
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsArray));

        Book book = Book.builder()
                .id(resultSet.getLong("book_id"))
                .title(resultSet.getString("book_title"))
                .authors(authors)
                .isbn(resultSet.getString("book_isbn"))
                .image(resultSet.getString("book_image"))
                .language(resultSet.getString("book_language"))
                .publisher(resultSet.getString("book_publisher"))
                .datePublished(resultSet.getDate("book_date_published"))
                .pages(resultSet.getInt("book_pages"))
                .synopsis(resultSet.getString("book_synopsis"))
                .owned(resultSet.getBoolean("book_owned"))
                .rating(resultSet.getInt("book_rating"))
                .comment(resultSet.getString("book_comment"))
                .friend(friend)
                .user(user)
                .build();
        String status = (resultSet.getString("book_status"));
        book.setStatus(BookStatus.valueOf(status));

        return book;
    }
}
