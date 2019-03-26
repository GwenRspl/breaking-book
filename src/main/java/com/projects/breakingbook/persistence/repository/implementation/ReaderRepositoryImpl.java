package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Collection;
import com.projects.breakingbook.persistence.entity.Reader;
import com.projects.breakingbook.persistence.entity.mapper.CollectionMapExtractor;
import com.projects.breakingbook.persistence.entity.mapper.ReaderMapExtractor;
import com.projects.breakingbook.persistence.entity.mapper.ReaderMapper;
import com.projects.breakingbook.persistence.repository.ReaderRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ReaderRepositoryImpl implements ReaderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO reader(reader_name, reader_avatar, reader_email, reader_password) VALUES (?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM reader";
    private final String SELECT_BY_ID = "SELECT * FROM reader WHERE reader_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM reader WHERE reader_id = ?";
    private final String DELETE_ALL = "DELETE FROM reader";
    private final String UPDATE = "UPDATE reader SET reader_name = ?, reader_avatar = ?, reader_email = ?, reader_password = ? WHERE reader_id = ?";

    private final String SELECT_JOIN  = "select * from reader " +
            "INNER JOIN book ON book.book_reader = reader.reader_id " +
            "INNER JOIN friend on book.book_friend = friend.friend_id;";

    private final String SELECT_JOIN_BY_ID  = "select * from reader " +
            "INNER JOIN book ON book.book_reader = reader.reader_id " +
            "INNER JOIN friend on book.book_friend = friend.friend_id " +
            "WHERE reader.reader_id = ?;";
    public ReaderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reader> findAllReaders() {
        List<Reader> readers = this.jdbcTemplate.query(SELECT_ALL, new ReaderMapper());
        Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new ReaderMapExtractor());
        for (Reader reader : readers) {
            reader.setBooks(booksMap.get(reader.getId()));
        }
        return readers;
    }

    @Override
    public boolean createReader(Reader reader) {
        int result = this.jdbcTemplate.update(INSERT, reader.getName(), reader.getAvatar(), reader.getEmail(), reader.getPassword());
        return result != 0;
    }

    @Override
    public Reader findReaderById(Long id) {
        Reader reader = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new ReaderMapper());
        Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new ReaderMapExtractor());
        reader.setBooks(booksMap.get(reader.getId()));
        return reader;
    }

    @Override
    public boolean deleteReaderById(Long id) {
        int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllReaders() {
        int result = this.jdbcTemplate.update(DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateReader(Long id, Reader reader) {
        int result = this.jdbcTemplate.update(UPDATE, reader.getName(), reader.getAvatar(), reader.getEmail(), reader.getPassword(), id);
        return result != 0;
    }
}
