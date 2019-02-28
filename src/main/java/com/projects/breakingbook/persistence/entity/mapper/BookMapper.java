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
                .id(resultSet.getLong("reader_id"))
                .build();
        // TODO : Check if only id is enough, need to change name of columns ?

        Friend friend = Friend.builder()
                .id(resultSet.getLong("friend_id"))
                .build();

        // TODO : Check if this is correct
        ArrayList<String> authors = new ArrayList(Arrays.asList(resultSet.getArray("authors")));
        ArrayList<String> subjects = new ArrayList(Arrays.asList(resultSet.getArray("subjects")));

        return Book.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .authors(authors)
                .isbn(resultSet.getString("isbn"))
                .image(resultSet.getString("image"))
                .language(resultSet.getString("language"))
                .publisher(resultSet.getString("publisher"))
                .datePublished(resultSet.getDate("date_published"))
                .edition(resultSet.getString("edition"))
                .page(resultSet.getInt("page"))
                .overview(resultSet.getString("overview"))
                .synopsis(resultSet.getString("synopsis"))
                .subjects(subjects)
                .reviews_api(resultSet.getString("reviews_api"))
                .reader(reader)
                .friend(friend)
                .build();
    }
}
