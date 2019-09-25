package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.RoleName;
import com.projects.breakingbook.persistence.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FriendMapExtractor implements ResultSetExtractor<Map<Long, List<Book>>> {
    @Override
    public Map<Long, List<Book>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, List<Book>> booksMap = new HashMap<>();
        while (resultSet.next()) {
            Long friendId = resultSet.getLong("friend_id");

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

            Book book =  Book.builder()
                    .id(resultSet.getLong("book_friend_book_id"))
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
                    .read(resultSet.getBoolean("book_read"))
                    .comment(resultSet.getString("book_comment"))
                    .friend(friend)
                    .user(user)
                    .build();

            List<Book> books = booksMap.get(friendId);
            if (books == null) {
                List<Book> newBooks = new ArrayList<>();

                newBooks.add(book);
                booksMap.put(friendId, newBooks);
            } else {
                books.add(book);
            }
        }
        return booksMap;
    }
}
