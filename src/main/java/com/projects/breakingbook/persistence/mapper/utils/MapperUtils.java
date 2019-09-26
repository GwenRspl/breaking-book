package com.projects.breakingbook.persistence.mapper.utils;

import com.projects.breakingbook.business.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapperUtils {

    public static Map<Long, List<Book>> generateBooksMap(final Map<Long, List<Book>> booksMap, final Long id, final Book book) {
        final List<Book> books = booksMap.get(id);
        if (books == null) {
            final List<Book> newBooks = new ArrayList<>();

            newBooks.add(book);
            booksMap.put(id, newBooks);
        } else {
            books.add(book);
        }
        return booksMap;
    }

    public static User generateUserFromResultSet(final ResultSet resultSet) throws SQLException {
        final User user = User.builder()
                .id(resultSet.getLong("breaking_book_user_id"))
                .username(resultSet.getString("breaking_book_user_username"))
                .avatar(resultSet.getString("breaking_book_user_avatar"))
                .email(resultSet.getString("breaking_book_user_email"))
                .password(resultSet.getString("breaking_book_user_password"))
                .build();
        final String role = (resultSet.getString("breaking_book_user_role"));
        user.setRole(RoleName.valueOf(role));
        return user;
    }

    public static Friend generateFriendFromResultSet(final ResultSet resultSet, final User user, final List<String> columnsNames) throws SQLException {
        return Friend.builder()
                .id(resultSet.getLong(columnsNames.get(0)))
                .name(resultSet.getString(columnsNames.get(1)))
                .avatar(resultSet.getString(columnsNames.get(2)))
                .user(user)
                .build();
    }

    public static Book generateBookFromResultSet(final ResultSet resultSet, final Friend friend, final User user, final String bookId) throws SQLException {
        final String[] authorsArray = (String[]) resultSet.getArray("book_authors").getArray();
        final ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsArray));

        final Book book = Book.builder()
                .id(resultSet.getLong(bookId))
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
        return book;
    }
}
