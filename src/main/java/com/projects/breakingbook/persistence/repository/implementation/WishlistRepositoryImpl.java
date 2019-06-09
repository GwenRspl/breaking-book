package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Wishlist;
import com.projects.breakingbook.persistence.mapper.WishlistMapExtractor;
import com.projects.breakingbook.persistence.mapper.WishlistMapper;
import com.projects.breakingbook.persistence.repository.WishlistRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class WishlistRepositoryImpl implements WishlistRepository {

    private static final String INSERT = new StringBuilder()
            .append("INSERT INTO wishlist")
            .append("(wishlist_name, wishlist_breaking_book_user)")
            .append(" VALUES ")
            .append("(?, ?)")
            .toString();
    private static final String SELECT_ALL = new StringBuilder()
            .append("SELECT ")
            .append("wishlist_id, wishlist_name, wishlist_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM wishlist INNER JOIN breaking_book_user r ON ")
            .append("wishlist.wishlist_breaking_book_user = r.breaking_book_user_id ")
            .append("WHERE wishlist_breaking_book_user = ?")
            .toString();
    private static final String SELECT_BY_ID = new StringBuilder()
            .append("SELECT ")
            .append("wishlist_id, wishlist_name, wishlist_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role ")
            .append("FROM wishlist INNER JOIN breaking_book_user r ON ")
            .append("wishlist.wishlist_breaking_book_user = r.breaking_book_user_id ")
            .append("WHERE wishlist_id = ?")
            .toString();
    private static final String DELETE_BY_ID = "DELETE FROM wishlist WHERE wishlist_id = ?";
    private static final String DELETE_ALL = "DELETE FROM wishlist";
    private static final String UPDATE = "UPDATE wishlist SET wishlist_name = ? WHERE wishlist_id = ?";
    private static final String SELECT_JOIN = new StringBuilder()
            .append("SELECT ")
            .append("wishlist_id, wishlist_name, wishlist_breaking_book_user, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user, ")
            .append("book_wishlist_wishlist_id, book_wishlist_book_id ")
            .append("FROM wishlist ")
            .append("LEFT JOIN book_wishlist ON wishlist.wishlist_id = book_wishlist.book_wishlist_wishlist_id ")
            .append("LEFT JOIN breaking_book_user r ON wishlist.wishlist_breaking_book_user = r.breaking_book_user_id ")
            .append("LEFT JOIN book ON book.book_id = book_wishlist.book_wishlist_book_id ")
            .append("FULL OUTER JOIN friend f ON book.book_friend = f.friend_id;")
            .toString();
    private static final String SELECT_JOIN_BY_ID = new StringBuilder()
            .append("SELECT ")
            .append("wishlist_id, wishlist_name, wishlist_breaking_book_user, ")
            .append("book_id, book_title, book_authors, book_isbn, book_image, book_language, ")
            .append("book_publisher, book_date_published, book_pages, book_synopsis, ")
            .append("book_breaking_book_user, book_friend, book_owned, book_rating, book_comment, book_status, ")
            .append("breaking_book_user_id, breaking_book_user_username, breaking_book_user_avatar, ")
            .append("breaking_book_user_email, breaking_book_user_password, breaking_book_user_role, ")
            .append("friend_id, friend_name, friend_avatar, friend_breaking_book_user, ")
            .append("book_wishlist_wishlist_id, book_wishlist_book_id ")
            .append("FROM wishlist ")
            .append("INNER JOIN book_wishlist ON wishlist.wishlist_id = book_wishlist.book_wishlist_wishlist_id ")
            .append("INNER JOIN book ON book.book_id = book_wishlist.book_wishlist_book_id ")
            .append("INNER JOIN breaking_book_user r ON book.book_breaking_book_user = r.breaking_book_user_id ")
            .append("FULL OUTER JOIN friend f ON book.book_friend = f.friend_id ")
            .append("WHERE wishlist_id = ?;")
            .toString();
    private static final String INSERT_BOOK_IN_WISHLIST = new StringBuilder()
            .append("INSERT INTO book_wishlist")
            .append("(book_wishlist_book_id, book_wishlist_wishlist_id) ")
            .append("VALUES ")
            .append("(?, ?);")
            .toString();
    private static final String REMOVE_BOOK_FROM_WISHLIST = new StringBuilder()
            .append("DELETE FROM book_wishlist ")
            .append("WHERE book_wishlist_book_id = ? AND book_wishlist_wishlist_id = ?;")
            .toString();
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Wishlist> findAllWishlists(final Long userId) {
        final List<Wishlist> wishlists = this.jdbcTemplate.query(SELECT_ALL, new Object[]{userId}, new WishlistMapper());
        if (wishlists.isEmpty()) {
            return wishlists;
        }
        final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new WishlistMapExtractor());
        if (!booksMap.isEmpty()) {
            for (final Wishlist wishlist : wishlists) {
                wishlist.setBooks(booksMap.get(wishlist.getId()));
            }
        }
        return wishlists;
    }

    @Override
    public boolean createWishlist(final Wishlist wishlist) {
        final int result = this.jdbcTemplate.update(INSERT, wishlist.getName(), wishlist.getUser().getId());
        return result != 0;
    }

    @Override
    public Optional<Wishlist> findWishlistById(final Long id) {
        try {
            final Wishlist wishlist = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new WishlistMapper());
            final Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new Object[]{id}, new WishlistMapExtractor());
            wishlist.setBooks(booksMap.get(wishlist.getId()));
            return Optional.of(wishlist);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteWishlistById(final Long id) {
        final int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllWishlists() {
        final int result = this.jdbcTemplate.update(DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateWishlist(final Long id, final Wishlist wishlist) {
        final int result = this.jdbcTemplate.update(UPDATE, wishlist.getName(), id);
        return result != 0;
    }

    @Override
    public boolean addBookToWishlist(final Long id, final Long bookId) {
        final int result = this.jdbcTemplate.update(INSERT_BOOK_IN_WISHLIST, bookId, id);
        return result != 0;
    }

    @Override
    public boolean removeBookFromWishlist(final Long id, final Long bookId) {
        final int result = this.jdbcTemplate.update(REMOVE_BOOK_FROM_WISHLIST, bookId, id);
        return result != 0;
    }
}
