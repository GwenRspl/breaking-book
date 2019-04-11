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
            "book_publisher, book_date_published, book_pages, book_synopsis, book_breaking_book_user, book_friend, book_owned, book_rating, book_comment) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM book INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id INNER JOIN " +
            "friend f on book.book_friend = f.friend_id";
    private final String SELECT_BY_ID = "SELECT * FROM book INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id INNER JOIN " +
            "friend f on book.book_friend = f.friend_id WHERE book_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM book WHERE book_id = ?";
    private final String DELETE_ALL = "DELETE FROM book";
    private final String UPDATE = "UPDATE book SET book_title = ?, book_authors = ?, book_isbn = ?, book_image = ?, " +
            "book_language = ?, book_publisher = ?, book_date_published = ?, book_pages = ?, book_synopsis = ?, " +
            "book_breaking_book_user = ?, book_friend = ?, book_owned = ?, book_rating = ?, book_comment = ? WHERE book_id = ?";

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooks() {
        return this.jdbcTemplate.query(SELECT_ALL, new BookMapper());
    }

    @Override
    public boolean createBook(Book book) {
        int result = this.jdbcTemplate.update(INSERT, book.getTitle(), convertListToSqlArray(book.getAuthors()), book.getIsbn(), book.getImage(),
                book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPages(), book.getSynopsis(), book.getUser().getId(), book.getFriend().getId(), book.isOwned(), book.getRating(), book.getComment());
        return result != 0;
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        try {
            return Optional.of(this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteBookById(Long id) {
        int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllBooks() {
        int result = this.jdbcTemplate.update(DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateBook(Long id, Book book) {
        int result = this.jdbcTemplate.update(UPDATE, book.getTitle(), convertListToSqlArray(book.getAuthors()), book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPages(), book.getSynopsis(), book.getUser().getId(), book.getFriend().getId(), book.isOwned(), book.getRating(), book.getComment(), id);
        return result != 0;
    }

    private Array convertListToSqlArray(List<String> listToConvert){
        Array authors = null;
        try {
            DataSource dataSource = this.jdbcTemplate.getDataSource();
            if(dataSource != null) authors = dataSource.getConnection().createArrayOf("VARCHAR", listToConvert.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
