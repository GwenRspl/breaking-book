package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.mapper.BookMapper;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public void createBook(Book book) {
        this.jdbcTemplate.update(INSERT, book.getTitle(), book.getAuthors(), book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPage(), book.getSynopsis(), book.getReader(), book.getFriend());
    }

    @Override
    public Book findBookById(Long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new BookMapper());
    }

    @Override
    public void deleteBookById(Long id) {
        this.jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void deleteAllBooks() {
        this.jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public void updateBook(Long id, Book book) {
        this.jdbcTemplate.update(UPDATE, book.getTitle(), book.getAuthors(), book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getPage(), book.getSynopsis(), book.getReader(), book.getFriend(), id);
    }
}
