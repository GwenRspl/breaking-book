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

    private static final String INSERT = new StringBuilder()
            .append("INSERT INTO collection")
            .append("(collection_name, collection_breaking_book_user)")
            .append(" VALUES ")
            .append("(?, ?)")
            .toString();
    private static final String SELECT_ALL = new StringBuilder()
            .append("SELECT ")
            .append("collection_id, collection_name, collection_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM collection ")
            .append("INNER JOIN breaking_book_user r ON ")
            .append("collection.collection_breaking_book_user = r.breaking_book_user_id ")
            .append("WHERE collection_breaking_book_user = ?")
            .toString();
    private static final String SELECT_BY_ID = new StringBuilder()
            .append("SELECT ")
            .append("collection_id, collection_name, collection_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM collection ")
            .append("INNER JOIN breaking_book_user r ON ")
            .append("collection.collection_breaking_book_user = r.breaking_book_user_id  ")
            .append("WHERE collection_id = ?")
            .toString();
    private static final String DELETE_BY_ID = "DELETE FROM collection WHERE collection_id = ?";
    private static final String UPDATE = "UPDATE collection SET collection_name = ? WHERE collection_id = ?";
    private static final String SELECT_JOIN = new StringBuilder()
            .append("SELECT ")
            .append("collection_id, collection_name, collection_breaking_book_user, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user, ")
            .append("book_collection_collection_id, book_collection_book_id ")
            .append("FROM collection ")
            .append("LEFT JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id ")
            .append("LEFT JOIN breaking_book_user r ON collection.collection_breaking_book_user = r.breaking_book_user_id ")
            .append("LEFT JOIN book ON book.book_id = book_collection.book_collection_book_id ")
            .append("FULL OUTER JOIN friend f ON book.book_friend = f.friend_id")
            .toString();
    private static final String SELECT_JOIN_BY_ID = new StringBuilder()
            .append("SELECT ")
            .append("collection_id, collection_name, collection_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user ")
            .append("book_collection_collection_id, book_collection_book_id ")
            .append("FROM collection ")
            .append("INNER JOIN book_collection ON collection.collection_id = book_collection.book_collection_collection_id ")
            .append("INNER JOIN breaking_book_user r ON collection.collection_breaking_book_user = r.breaking_book_user_id ")
            .append("INNER JOIN book ON book.book_id = book_collection.book_collection_book_id ")
            .append("FULL OUTER JOIN friend f ON book.book_friend = f.friend_id ")
            .append("WHERE collection_id = ?;")
            .toString();
    private static final String INSERT_BOOK_IN_COLLECTION = new StringBuilder()
            .append("INSERT INTO book_collection")
            .append("(book_collection_book_id, book_collection_collection_id) ")
            .append("VALUES ")
            .append("(?, ?);")
            .toString();
    private static final String REMOVE_BOOK_FROM_COLLECTION = new StringBuilder()
            .append("DELETE FROM book_collection ")
            .append("WHERE book_collection_book_id = ? ")
            .append("AND book_collection_collection_id = ?;")
            .toString();
    private final JdbcTemplate jdbcTemplate;

    public CollectionRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Collection> findAllCollections(final Long userId) {
        final List<Collection> collections = this.jdbcTemplate.query(SELECT_ALL, new Object[]{userId}, new CollectionMapper());
        if (collections.isEmpty()) {
            return collections;
        }
        final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new CollectionMapExtractor());
        if (!booksMap.isEmpty()) {
            for (final Collection collection : collections) {
                collection.setBooks(booksMap.get(collection.getId()));
            }
        }
        return collections;
    }

    @Override
    public boolean createCollection(final Collection collection) {
        final int result = this.jdbcTemplate.update(INSERT, collection.getName(), collection.getUser().getId());
        return result != 0;
    }

    @Override
    public Optional<Collection> findCollectionById(final Long id) {
        try {
            final Collection collection = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new CollectionMapper());
            final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new Object[]{id}, new CollectionMapExtractor());
            collection.setBooks(booksMap.get(collection.getId()));
            return Optional.of(collection);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCollectionById(final Long id) {
        final int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean updateCollection(final Long id, final Collection collection) {
        final int result = this.jdbcTemplate.update(UPDATE, collection.getName(), id);
        return result != 0;
    }

    @Override
    public boolean addBookToCollection(final Long id, final Long bookId) {
        final int result = this.jdbcTemplate.update(INSERT_BOOK_IN_COLLECTION, bookId, id);
        return result != 0;
    }

    @Override
    public boolean removeBookFromCollection(final Long id, final Long bookId) {
        final int result = this.jdbcTemplate.update(REMOVE_BOOK_FROM_COLLECTION, bookId, id);
        return result != 0;
    }
}
