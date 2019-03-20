package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.Reader;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Reader reader = Reader.builder()
                .id(resultSet.getLong("book_reader"))
                .name(resultSet.getString("reader_name"))
                .avatar(resultSet.getString("reader_avatar"))
                .email(resultSet.getString("reader_email"))
                .password(resultSet.getString("reader_password"))
                .build();

        Friend friend = Friend.builder()
                .id(resultSet.getLong("book_friend"))
                .name(resultSet.getString("friend_name"))
                .avatar(resultSet.getString("friend_avatar"))
                .reader(reader)
                .build();

        String[] authorsArray = (String[]) resultSet.getArray("book_authors").getArray();
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsArray));

        return Book.builder()
                .id(resultSet.getLong("book_id"))
                .title(resultSet.getString("book_title"))
                .authors(authors)
                .isbn(resultSet.getString("book_isbn"))
                .image(resultSet.getString("book_image"))
                .language(resultSet.getString("book_language"))
                .publisher(resultSet.getString("book_publisher"))
                .datePublished(resultSet.getDate("book_date_published"))
                .pages(resultSet.getInt("book_page"))
                .synopsis(resultSet.getString("book_synopsis"))
                .reader(reader)
                .friend(friend)
                .build();
    }
}
