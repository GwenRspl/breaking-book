package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.mapper.BookMapper;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO book(book_title, book_authors, book_isbn, book_image, book_language, " +
            "book_publisher, book_date_published, book_pages, book_synopsis, book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM book INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id FULL OUTER JOIN " +
            "friend f on book.book_friend = f.friend_id WHERE book.book_breaking_book_user = ?";
    private final String SELECT_BY_ID = "SELECT * FROM book INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id FULL OUTER JOIN " +
            "friend f on book.book_friend = f.friend_id WHERE book_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM book WHERE book_id = ?";
    private final String DELETE_ALL = "DELETE FROM book";
    private final String UPDATE = "UPDATE book SET book_title = ?, book_authors = ?, book_isbn = ?, book_image = ?, " +
            "book_language = ?, book_publisher = ?, book_date_published = ?, book_pages = ?, book_synopsis = ?, " +
            "book_breaking_book_user = ?, book_friend = ?, book_owned = ?, book_rating = ?, book_comment = ?, book_status = ? WHERE book_id = ?";

    private final String UPDATE_OWNED = "UPDATE book SET book_owned = ? WHERE book_id = ?";
    private final String UPDATE_FRIEND = "UPDATE book SET book_friend = ? WHERE book_id = ?";

    public BookRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooks(final Long userId) {
        return this.jdbcTemplate.query(this.SELECT_ALL, new Object[]{userId}, new BookMapper());
    }

    @Override
    public boolean createBook(final Book book) {
        final int result = this.jdbcTemplate.update(this.INSERT, book.getTitle(), this.convertListToSqlArray(book.getAuthors()), book.getIsbn(), book.getImage(),
                book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPages(), book.getSynopsis(), book.getUser().getId(), book.getFriend().getId(), book.isOwned(), book.getRating(), book.getComment(), book.getStatus());
        return result != 0;
    }

    @Override
    public Optional<Book> findBookById(final Long id) {
        try {
            return Optional.of(this.jdbcTemplate.queryForObject(this.SELECT_BY_ID, new Object[]{id}, new BookMapper()));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteBookById(final Long id) {
        final int result = this.jdbcTemplate.update(this.DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllBooks() {
        final int result = this.jdbcTemplate.update(this.DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateBook(final Long id, final Book book) {
        final int result = this.jdbcTemplate.update(this.UPDATE, book.getTitle(), this.convertListToSqlArray(book.getAuthors()), book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPages(), book.getSynopsis(), book.getUser().getId(), book.getFriend().getId(), book.isOwned(), book.getRating(), book.getComment(), book.getStatus(), id);
        return result != 0;
    }

    @Override
    public boolean toggleOwned(final Long id) {
        final Optional<Book> optionalBook = this.findBookById(id);
        Book book = null;
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            final int result = this.jdbcTemplate.update(this.UPDATE_OWNED, !book.isOwned(), id);
            return result != 0;
        } else {
            return false;
        }

    }

    @Override
    public boolean updateFriend(final Long bookId, final Long friendId) {
        final int result = this.jdbcTemplate.update(this.UPDATE_FRIEND, friendId, bookId);
        return result != 0;
    }

    private Array convertListToSqlArray(final List<String> listToConvert) {
        Array authors = null;
        try {
            final DataSource dataSource = this.jdbcTemplate.getDataSource();
            if (dataSource != null) {
                authors = dataSource.getConnection().createArrayOf("VARCHAR", listToConvert.toArray());
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
