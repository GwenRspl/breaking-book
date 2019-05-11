package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Collection;
import com.projects.breakingbook.persistence.entity.mapper.CollectionMapExtractor;
import com.projects.breakingbook.persistence.entity.mapper.CollectionMapper;
import com.projects.breakingbook.persistence.repository.CollectionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class CollectionRepositoryImpl implements CollectionRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String INSERT = "INSERT INTO collection(collection_name) VALUES (?)";
    private final String SELECT_ALL = "SELECT * FROM collection WHERE collection_breaking_book_user = ?";
    private final String SELECT_BY_ID = "SELECT * FROM collection WHERE collection_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM collection WHERE collection_id = ?";
    private final String DELETE_ALL = "DELETE FROM collection";
    private final String UPDATE = "UPDATE collection SET collection_name = ? WHERE collection_id = ?";

    private final String SELECT_JOIN = "SELECT * FROM collection " +
            "INNER JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id " +
            "INNER JOIN book ON book.book_id = book_collection.book_collection_book_id " +
            "INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id " +
            "FULL OUTER JOIN friend f ON book.book_friend = f.friend_id";

    private final String SELECT_JOIN_BY_ID = "SELECT * FROM collection " +
            "INNER JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id " +
            "INNER JOIN book ON book.book_id = book_collection.book_collection_book_id " +
            "INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id " +
            "FULL OUTER JOIN friend f ON book.book_friend = f.friend_id " +
            "WHERE collection_id = ?;";

    public CollectionRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Collection> findAllCollections(final Long userId) {
        final List<Collection> collections = this.jdbcTemplate.query(this.SELECT_ALL, new Object[]{userId}, new CollectionMapper());
        final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(this.SELECT_JOIN, new CollectionMapExtractor());
        for (final Collection collection : collections) {
            collection.setBooks(booksMap.get(collection.getId()));
        }
        return collections;
    }

    @Override
    public boolean createCollection(final Collection collection) {
        final int result = this.jdbcTemplate.update(this.INSERT, collection.getName());
        return result != 0;
    }

    @Override
    public Optional<Collection> findCollectionById(final Long id) {
        try {
            final Collection collection = this.jdbcTemplate.queryForObject(this.SELECT_BY_ID, new Object[]{id}, new CollectionMapper());
            final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(this.SELECT_JOIN_BY_ID, new Object[]{id}, new CollectionMapExtractor());
            collection.setBooks(booksMap.get(collection.getId()));
            return Optional.of(collection);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCollectionById(final Long id) {
        final int result = this.jdbcTemplate.update(this.DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllCollections() {
        final int result = this.jdbcTemplate.update(this.DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateCollection(final Long id, final Collection collection) {
        final int result = this.jdbcTemplate.update(this.UPDATE, collection.getName(), id);
        return result != 0;
    }
}
