package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.entity.Wishlist;
import com.projects.breakingbook.business.service.WishlistService;
import com.projects.breakingbook.persistence.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistServiceImpl(final WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public List<Wishlist> getAll(final Long userId) {
        return this.wishlistRepository.findAllWishlists(userId);
    }

    @Override
    public Optional<Wishlist> getOne(final Long id) {
        return this.wishlistRepository.findWishlistById(id);
    }

    @Override
    public boolean create(final Wishlist wishlist) {
        return this.wishlistRepository.createWishlist(wishlist);
    }

    @Override
    public boolean update(final Long id, final Wishlist wishlist) {
        return this.wishlistRepository.updateWishlist(id, wishlist);
    }

    @Override
    public boolean delete(final Long id) {
        return this.wishlistRepository.deleteWishlistById(id);
    }

    @Override
    public boolean deleteAll() {
        return this.wishlistRepository.deleteAllWishlists();
    }

    @Override
    public boolean addBookToWishlist(final Long id, final Long bookId) {
        return this.wishlistRepository.addBookToWishlist(id, bookId);
    }

    @Override
    public boolean removeBookFromWishlist(final Long id, final Long bookId) {
        return this.wishlistRepository.removeBookFromWishlist(id, bookId);
    }
}
