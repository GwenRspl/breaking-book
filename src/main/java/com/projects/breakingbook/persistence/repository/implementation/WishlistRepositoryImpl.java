package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Wishlist;
import com.projects.breakingbook.persistence.entity.mapper.WishlistMapExtractor;
import com.projects.breakingbook.persistence.entity.mapper.WishlistMapper;
import com.projects.breakingbook.persistence.repository.WishlistRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class WishlistRepositoryImpl implements WishlistRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String INSERT = "INSERT INTO wishlist(wishlist_name, wishlist_reader) VALUES (?, ?)";
    private final String SELECT_ALL = "SELECT * FROM wishlist INNER JOIN reader r ON " +
            "wishlist.wishlist_reader = r.reader_id";
    private final String SELECT_BY_ID = "SELECT * FROM wishlist INNER JOIN reader r ON " +
            "wishlist.wishlist_reader = r.reader_id WHERE wishlist_id = ?";
    private final String DELETE_BY_ID = "DELETE FROM wishlist WHERE wishlist_id = ?";
    private final String DELETE_ALL = "DELETE FROM wishlist";
    private final String UPDATE = "UPDATE wishlist SET wishlist_name = ? WHERE wishlist_id = ?";

    private final String SELECT_JOIN  = "select * from wishlist " +
            "inner join book_wishlist on wishlist.wishlist_id = book_wishlist.book_wishlist_wishlist_id " +
            "inner join book on book.book_id = book_wishlist.book_wishlist_book_id " +
            "INNER JOIN reader r ON book.book_reader = r.reader_id " +
            "INNER JOIN friend f on book.book_friend = f.friend_id;";

    private final String SELECT_JOIN_BY_ID = "SELECT * FROM wishlist inner join book_wishlist on wishlist.wishlist_id = book_wishlist.book_wishlist_wishlist_id inner join book on book.book_id = book_wishlist.book_wishlist_book_id INNER JOIN reader r ON book.book_reader = r.reader_id INNER JOIN friend f on book.book_friend = f.friend_id;";


    public WishlistRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Wishlist> findAllWishlists() {
        List<Wishlist> wishlists =  this.jdbcTemplate.query(SELECT_ALL, new WishlistMapper());
        Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN, new WishlistMapExtractor());
        for (Wishlist wishlist : wishlists) {
            wishlist.setBooks(booksMap.get(wishlist.getId()));
        }
        return wishlists;
    }

    @Override
    public boolean createWishlist(Wishlist wishlist) {
        int result = this.jdbcTemplate.update(INSERT, wishlist.getName(), wishlist.getReader().getId());
        return result != 0;
    }

    @Override
    public Wishlist findWishlistById(Long id) {
        Wishlist wishlist = this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new WishlistMapper());
        Map<Long, List<Book>> booksMap = this.jdbcTemplate.query(SELECT_JOIN_BY_ID, new WishlistMapExtractor());
        wishlist.setBooks(booksMap.get(wishlist.getId()));
        return wishlist;
    }

    @Override
    public boolean deleteWishlistById(Long id) {
        int result = this.jdbcTemplate.update(DELETE_BY_ID, id);
        return result != 0;
    }

    @Override
    public boolean deleteAllWishlists() {
        int result = this.jdbcTemplate.update(DELETE_ALL);
        return result != 0;
    }

    @Override
    public boolean updateWishlist(Long id, Wishlist wishlist) {
        int result = this.jdbcTemplate.update(UPDATE, wishlist.getName(), id);
        return result != 0;
    }
}
