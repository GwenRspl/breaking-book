package com.projects.breakingbook.business.service;

import com.projects.breakingbook.business.entity.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {

    List<Wishlist> getAll(final Long id);

    Optional<Wishlist> getOne(final Long id);

    boolean create(final Wishlist wishlist);

    boolean update(final Long id, final Wishlist wishlist);

    boolean delete(final Long id);

    boolean addBookToWishlist(Long id, Long bookId);

    boolean removeBookFromWishlist(Long id, Long bookId);
}
