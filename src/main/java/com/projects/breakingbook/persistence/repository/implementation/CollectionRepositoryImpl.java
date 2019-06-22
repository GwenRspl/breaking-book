package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Collection;
import com.projects.breakingbook.persistence.mapper.CollectionMapExtractor;
import com.projects.breakingbook.persistence.mapper.CollectionMapper;
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

    private final String INSERT = "INSERT INTO collection(collection_name, collection_breaking_book_user) VALUES (?, ?)";
    private final String SELECT_ALL = "SELECT * FROM collection INNER JOIN breaking_book_user r ON " +
            "collection.collection_breaking_book_user = r.breaking_book_user_id WHERE collection_breaking_book_user = ?";
    private final String SELECT_BY_ID = "SELECT * FROM collection INNER JOIN breaking_book_user r ON " +
            "collection.collection_breaking_book_user = r.breaking_book_user_id  WHERE collection_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM collection WHERE collection_id = ?";
    private final String DELETE_ALL = "DELETE FROM collection";
    private final String UPDATE = "UPDATE collection SET collection_name = ? WHERE collection_id = ?";

    private final String SELECT_JOIN = "SELECT * FROM collection " +
            "LEFT JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id " +
            "LEFT JOIN breaking_book_user r ON collection.collection_breaking_book_user = r.breaking_book_user_id " +
            "LEFT JOIN book ON book.book_id = book_collection.book_collection_book_id " +
            "FULL OUTER JOIN friend f ON book.book_friend = f.friend_id";

    private final String SELECT_JOIN_BY_ID = "SELECT * FROM collection " +
            "INNER JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id " +
            "INNER JOIN breaking_book_user r ON collection.collection_breaking_book_user = r.breaking_book_user_id " +
            "INNER JOIN book ON book.book_id = book_collection.book_collection_book_id " +
            "FULL OUTER JOIN friend f ON book.book_friend = f.friend_id " +
            "WHERE collection_id = ?;";

    private final String INSERT_BOOK_IN_COLLECTION = "INSERT INTO book_collection(book_collection_book_id, book_collection_collection_id) VALUES (?, ?);";
    private final String REMOVE_BOOK_FROM_COLLECTION = "DELETE FROM book_collection WHERE book_collection_book_id = ? AND book_collection_collection_id = ?;";

    public CollectionRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Collection> findAllCollections(final Long userId) {
        final List<Collection> collections = this.jdbcTemplate.query(this.SELECT_ALL, new Object[]{userId}, new CollectionMapper());
        if (collections.isEmpty()) {
            return collections;
        }
        final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(this.SELECT_JOIN, new CollectionMapExtractor());
        if (!booksMap.isEmpty()) {
            for (final Collection collection : collections) {
                collection.setBooks(booksMap.get(collection.getId()));
            }
        }
        return collections;
    }

    @Override
    public boolean createCollection(final Collection collection) {
        final int result = this.jdbcTemplate.update(this.INSERT, collection.getName(), collection.getUser().getId());
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

    @Override
    public boolean addBookToCollection(final Long id, final Long bookId) {
        final int result = this.jdbcTemplate.update(this.INSERT_BOOK_IN_COLLECTION, bookId, id);
        return result != 0;
    }

    @Override
    public boolean removeBookFromCollection(final Long id, final Long bookId) {
        final int result = this.jdbcTemplate.update(this.REMOVE_BOOK_FROM_COLLECTION, bookId, id);
        return result != 0;
    }
}
