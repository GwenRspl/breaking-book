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
    private final String INSERT = "INSERT INTO book(title, authors, isbn, image, language, publisher, date_published, edition, page, overview, synopsis, subjects, reviews_api, reader, friend) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM book INNER JOIN reader r ON book.reader = r.id INNER JOIN friend f on book.friend = f.id";
    private final String SELECT_BY_ID = "SELECT * FROM book INNER JOIN reader r ON book.reader = r.id INNER JOIN friend f on book.friend = f.id WHERE id = ?";
    private final String DELETE_BY_ID = "DELETE FROM book WHERE id = ?";
    private final String DELETE_ALL = "DELETE FROM book";
    private final String UPDATE = "UPDATE book SET title = ?, authors = ?, isbn = ?, image = ?, language = ?, publisher = ?, date_published = ?, edition = ?, page = ?, overview = ?, synopsis = ?, subjects = ?, reviews_api = ?, reader = ?, friend = ? WHERE id = ?";

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAllBooks() {
        return this.jdbcTemplate.query(SELECT_ALL, new BookMapper());
    }

    @Override
    public void createBook(Book book) {
        this.jdbcTemplate.update(INSERT, book.getTitle(), book.getAuthors(), book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getEdition(), book.getPage(), book.getOverview(), book.getSynopsis(), book.getSubjects(), book.getReviews_api(), book.getReader(), book.getFriend());
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
        this.jdbcTemplate.update(UPDATE, book.getTitle(), book.getAuthors(), book.getIsbn(), book.getImage(), book.getLanguage(), book.getPublisher(), book.getDatePublished(), book.getEdition(), book.getPage(), book.getOverview(), book.getSynopsis(), book.getSubjects(), book.getReviews_api(), book.getReader(), book.getFriend(), id);
    }
}
