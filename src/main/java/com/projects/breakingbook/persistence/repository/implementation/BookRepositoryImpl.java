package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.persistence.mapper.BookMapper;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);
    private static final String INSERT = new StringBuilder()
            .append("INSERT INTO book")
            .append("(book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, ")
            .append("book_comment, book_status) ")
            .append("VALUES ")
            .append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
            .toString();
    private static final String SELECT_ALL = new StringBuilder()
            .append("SELECT ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user ")
            .append("FROM book ")
            .append("INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id ")
            .append("FULL OUTER JOIN friend f on book.book_friend = f.friend_id WHERE book.book_breaking_book_user = ?")
            .toString();
    private static final String SELECT_BY_ID = new StringBuilder()
            .append("SELECT ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, book_breaking_book_user, book_friend, ")
            .append("book_owned, book_rating, book_comment, book_status, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user ")
            .append("FROM book INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id ")
            .append("FULL OUTER JOIN friend f on book.book_friend = f.friend_id WHERE book_id = ?")
            .toString();
    private static final String DELETE_BY_ID = "DELETE FROM book WHERE book_id = ?";
    private static final String UPDATE = new StringBuilder()
            .append("UPDATE book ")
            .append("SET book_title = ?, book_authors = ?, book_isbn = ?, book_image = ?, book_language = ?, ")
            .append("book_publisher = ?, book_date_published = ?, book_pages = ?, book_synopsis = ?, ")
            .append("book_breaking_book_user = ?, book_friend = ?, book_owned = ?, ")
            .append("book_rating = ?, book_comment = ?, book_status = ? ")
            .append("WHERE book_id = ?")
            .toString();
    private static final String UPDATE_OWNED = "UPDATE book SET book_owned = ? WHERE book_id = ?";
    private static final String UPDATE_FRIEND = "UPDATE book SET book_friend = ? WHERE book_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooks(final Long userId) {
        return this.jdbcTemplate.query(SELECT_ALL, new Object[]{userId}, new BookMapper());
    }

    @Override
    public Long createBook(final Book book) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection
                    .prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setArray(2, this.convertListToSqlArray(book.getAuthors()));
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getImage());
            ps.setString(5, book.getLanguage());
            ps.setString(6, book.getPublisher());
            if (book.getDatePublished() != null) {
                ps.setDate(7, new Date(book.getDatePublished().getTime()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            ps.setInt(8, book.getPages());
            ps.setString(9, book.getSynopsis());
            ps.setLong(10, book.getUser().getId());
            if (book.getFriend() != null) {
                ps.setLong(11, book.getFriend().getId());
            } else {
                ps.setNull(11, Types.BIGINT);
            }
            ps.setBoolean(12, book.isOwned());
            ps.setInt(13, book.getRating());
            ps.setString(14, book.getComment());
            ps.setString(15, book.getStatus().getBookStatusString());
            return ps;
        }, keyHolder);

        final Long newId;
        if (keyHolder.getKeys().size() > 1) {
            newId = ((Integer) keyHolder.getKeys().get("book_id")).longValue();
        } else {
            newId = keyHolder.getKey().longValue();
        }

        return newId;
    }

    @Override
    public Optional<Book> findBookById(final Long id) {
        try {
            return Optional.of(this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new BookMapper()));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteBookById(final Long id) {
        final int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean updateBook(final Long id, final Book book) {
        final int result = this.jdbcTemplate.update(UPDATE, book.getTitle(), this.convertListToSqlArray(book.getAuthors()),
                book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(),
                book.getPages(), book.getSynopsis(), book.getUser().getId(), book.getFriend() != null ? book.getFriend().getId() : null, book.isOwned(),
                book.getRating(), book.getComment(), book.getStatus() != null ? book.getStatus().getBookStatusString() : null, id);
        return result != 0;
    }

    @Override
    public boolean toggleOwned(final Long id) {
        final Optional<Book> optionalBook = this.findBookById(id);
        Book book = null;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            final int result = this.jdbcTemplate.update(UPDATE_OWNED, !book.isOwned(), id);
            return result != 0;
        } else {
            return false;
        }

    }

    @Override
    public boolean updateFriend(final Long bookId, final Long friendId) {
        final int result = this.jdbcTemplate.update(UPDATE_FRIEND, friendId, bookId);
        return result != 0;
    }

    private Array convertListToSqlArray(final List<String> listToConvert) {
        Array authors = null;
        try {
            final DataSource dataSource = this.jdbcTemplate.getDataSource();
            if (dataSource != null) {
                try (final Connection connection = dataSource.getConnection()) {
                    authors = connection.createArrayOf("VARCHAR", listToConvert.toArray());
                }
            }
        } catch (final SQLException e) {
            LOGGER.error("Cannot convert list to SQL array", e);
        }
        return authors;
    }
}
