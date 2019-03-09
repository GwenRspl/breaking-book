package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.mapper.BookMapper;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO book(book_title, book_authors, book_isbn, book_image, book_language, " +
            "book_publisher, book_date_published, book_page, book_synopsis, book_reader, book_friend) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM book INNER JOIN reader r ON book.book_reader = r.reader_id INNER JOIN " +
            "friend f on book.book_friend = f.friend_id";
    private final String SELECT_BY_ID = "SELECT * FROM book INNER JOIN reader r ON book.book_reader = r.reader_id INNER JOIN " +
            "friend f on book.book_friend = f.friend_id WHERE book_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM book WHERE book_id = ?";
    private final String DELETE_ALL = "DELETE FROM book";
    private final String UPDATE = "UPDATE book SET book_title = ?, book_authors = ?, book_isbn = ?, book_image = ?, " +
            "book_language = ?, book_publisher = ?, book_date_published = ?, book_page = ?, book_synopsis = ?, " +
            "book_reader = ?, book_friend = ? WHERE book_id = ?";

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooks() {
        return this.jdbcTemplate.query(SELECT_ALL, new BookMapper());
    }

    @Override
    public boolean createBook(Book book) {
        Array authors = null;
        try {
            authors = this.jdbcTemplate.getDataSource().getConnection().createArrayOf("VARCHAR", book.getAuthors().toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int result = this.jdbcTemplate.update(INSERT, book.getTitle(), authors, book.getIsbn(), book.getImage(),
                book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPage(), book.getSynopsis(),
                book.getReader(), book.getFriend());
        return result != 0;
    }

    @Override
    public Book findBookById(Long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new BookMapper());
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
        int result = this.jdbcTemplate.update(UPDATE, book.getTitle(), book.getAuthors(), book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPage(), book.getSynopsis(), book.getReader(), book.getFriend(), id);
        return result != 0;
    }
}
