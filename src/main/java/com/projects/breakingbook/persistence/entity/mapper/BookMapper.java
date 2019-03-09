package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.Reader;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Reader reader = Reader.builder()
                .id(resultSet.getLong("book_reader"))
                .build();
        // TODO : Check if only id is enough, need to change name of columns ?

        Friend friend = Friend.builder()
                .id(resultSet.getLong("book_friend"))
                .build();

        // TODO : Check if this is correct
  /*      ArrayList<String> authors = new ArrayList(Arrays.asList(resultSet.getArray("book_authors")));*/
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
                .page(resultSet.getInt("book_page"))
                .synopsis(resultSet.getString("book_synopsis"))
                .reader(reader)
                .friend(friend)
                .build();
    }
}
