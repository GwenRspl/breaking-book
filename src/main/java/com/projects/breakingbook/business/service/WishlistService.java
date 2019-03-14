package com.projects.breakingbook.business.service;

import com.projects.breakingbook.persistence.entity.Wishlist;

import java.util.List;

public interface WishlistService {

    List<Wishlist> getAll();
    Wishlist getOne(final Long id);
    boolean create(final Wishlist wishlist);
    boolean update(final Long id, final Wishlist wishlist);
    boolean delete(final Long id);
    boolean deleteAll();
}
