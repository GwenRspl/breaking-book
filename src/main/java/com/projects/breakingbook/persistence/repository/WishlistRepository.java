package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository {

    List<Wishlist> findAllWishlists(Long userId);

    boolean createWishlist(Wishlist wishlist);

    Optional<Wishlist> findWishlistById(Long id);

    boolean deleteWishlistById(Long id);

    boolean deleteAllWishlists();

    boolean updateWishlist(Long id, Wishlist wishlist);

    boolean addBookToWishlist(Long id, Long bookId);

    boolean removeBookFromWishlist(Long id, Long bookId);
}
