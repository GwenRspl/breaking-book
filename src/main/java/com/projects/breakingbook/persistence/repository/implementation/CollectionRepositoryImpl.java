package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Collection;
import com.projects.breakingbook.persistence.entity.mapper.CollectionMapExtractor;
import com.projects.breakingbook.persistence.entity.mapper.CollectionMapper;
import com.projects.breakingbook.persistence.repository.BookRepository;
import com.projects.breakingbook.persistence.repository.CollectionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class CollectionRepositoryImpl implements CollectionRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String INSERT = "INSERT INTO collection(collection_name) VALUES (?)";
    private final String SELECT_ALL = "SELECT * FROM collection";
    private final String SELECT_BY_ID = "SELECT * FROM collection WHERE collection_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM collection WHERE collection_id = ?";
    private final String DELETE_ALL = "DELETE FROM collection";
    private final String UPDATE = "UPDATE collection SET collection_name = ? WHERE collection_id = ?";

    private final String SELECT_JOIN = "SELECT * FROM collection " +
            "INNER JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id " +
            "INNER JOIN book ON book.book_id = book_collection.book_collection_book_id " +
            "INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id " +
            "INNER JOIN friend f ON book.book_friend = f.friend_id;";

    private final String SELECT_JOIN_BY_ID = "SELECT * FROM collection " +
            "INNER JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id " +
            "INNER JOIN book ON book.book_id = book_collection.book_collection_book_id " +
            "INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id " +
            "INNER JOIN friend f ON book.book_friend = f.friend_id " +
            "WHERE collection_id = ?;";

    public CollectionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Collection> findAllCollections() {
        List<Collection> collections = this.jdbcTemplate.query(SELECT_ALL, new CollectionMapper());
        Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new CollectionMapExtractor());
        for (Collection collection : collections) {
            collection.setBooks(booksMap.get(collection.getId()));
        }
        return collections;
    }

    @Override
    public boolean createCollection(Collection collection) {
        int result = this.jdbcTemplate.update(INSERT, collection.getName());
        return result != 0;
    }

    @Override
    public Optional<Collection> findCollectionById(Long id) {
        try {
            Collection collection = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new CollectionMapper());
            Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new Object[]{id}, new CollectionMapExtractor());
            collection.setBooks(booksMap.get(collection.getId()));
            return Optional.of(collection);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCollectionById(Long id) {
        int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllCollections() {
        int result = this.jdbcTemplate.update(DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateCollection(Long id, Collection collection) {
        int result = this.jdbcTemplate.update(UPDATE, collection.getName(), id);
        return result != 0;
    }
}
