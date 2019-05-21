package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CollectionMapExtractor implements ResultSetExtractor<Map<Long, List<Book>>> {
    @Override
    public Map<Long, List<Book>> extractData(final ResultSet resultSet) throws SQLException, DataAccessException {
        final Map<Long, List<Book>> booksMap = new HashMap<>();

        while (resultSet.next()) {
            if (resultSet.getLong("book_collection_book_id") == 0) {
                return booksMap;
            }
            final Long collectionId = resultSet.getLong("collection_id");

            final User user = User.builder()
                    .id(resultSet.getLong("breaking_book_user_id"))
                    .username(resultSet.getString("breaking_book_user_username"))
                    .avatar(resultSet.getString("breaking_book_user_avatar"))
                    .email(resultSet.getString("breaking_book_user_email"))
                    .password(resultSet.getString("breaking_book_user_password"))
                    .build();
            final String role = (resultSet.getString("breaking_book_user_role"));
            user.setRole(RoleName.valueOf(role));

            final Friend friend = Friend.builder()
                    .id(resultSet.getLong("book_friend"))
                    .name(resultSet.getString("friend_name"))
                    .avatar(resultSet.getString("friend_avatar"))
                    .user(user)
                    .build();

            ArrayList<String> authors = null;
            if (resultSet.getArray("book_authors") != null) {
                final String[] authorsArray = (String[]) resultSet.getArray("book_authors").getArray();
                authors = new ArrayList<>(Arrays.asList(authorsArray));

            }

            final Book book = Book.builder()
                    .id(resultSet.getLong("book_collection_book_id"))
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
            final String status = (resultSet.getString("book_status"));
            if (status != null) {
                book.setStatus(BookStatus.valueOf(status));
            }

            final List<Book> books = booksMap.get(collectionId);
            if (books == null) {
                final List<Book> newBooks = new ArrayList<>();

                newBooks.add(book);
                booksMap.put(collectionId, newBooks);
            } else {
                books.add(book);
            }
        }
        return booksMap;
    }
}
