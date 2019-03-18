package com.projects.breakingbook.persistence.repository.implementation;

import com.projects.breakingbook.persistence.entity.Wishlist;
import com.projects.breakingbook.persistence.entity.mapper.WishlistMapper;
import com.projects.breakingbook.persistence.repository.WishlistRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public WishlistRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Wishlist> findAllWishlists() {
        return this.jdbcTemplate.query(SELECT_ALL, new WishlistMapper());
    }

    @Override
    public boolean createWishlist(Wishlist wishlist) {
        int result = this.jdbcTemplate.update(INSERT, wishlist.getName(), wishlist.getReader().getId());
        return result != 0;
    }

    @Override
    public Wishlist findWishlistById(Long id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID, new Object [] {id}, new WishlistMapper());
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
