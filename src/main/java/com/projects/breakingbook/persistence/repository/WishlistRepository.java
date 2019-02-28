package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Wishlist;

import java.util.List;

public interface WishlistRepository {

    List<Wishlist> findAllWishlists();
    void createWishlist(Wishlist wishlist);
    Wishlist findWishlistById(Long id);
    void deleteWishlistById(Long id);
    void deleteAllWishlists();
    void updateWishlist(Long id, Wishlist wishlist);
}
