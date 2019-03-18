package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Reader;
import com.projects.breakingbook.persistence.entity.mapper.ReaderMapper;
import com.projects.breakingbook.persistence.repository.ReaderRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public ReaderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reader> findAllReaders() {
        return this.jdbcTemplate.query(SELECT_ALL, new ReaderMapper());
    }

    @Override
    public boolean createReader(Reader reader) {
        int result = this.jdbcTemplate.update(INSERT, reader.getName(), reader.getAvatar(), reader.getEmail(), reader.getPassword());
        return result != 0;
    }

    @Override
    public Reader findReaderById(Long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new ReaderMapper());

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
