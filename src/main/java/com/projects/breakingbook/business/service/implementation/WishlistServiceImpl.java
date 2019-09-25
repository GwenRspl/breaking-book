package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.WishlistService;
import com.projects.breakingbook.persistence.entity.Wishlist;
import com.projects.breakingbook.persistence.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private WishlistRepository wishlistRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public List<Wishlist> getAll() {
        return this.wishlistRepository.findAllWishlists();
    }

    @Override
    public Optional<Wishlist> getOne(Long id) {
        return this.wishlistRepository.findWishlistById(id);
    }

    @Override
    public boolean create(Wishlist wishlist) {
        return this.wishlistRepository.createWishlist(wishlist);
    }

    @Override
    public boolean update(Long id, Wishlist wishlist) {
        return this.wishlistRepository.updateWishlist(id, wishlist);
    }

    @Override
    public boolean delete(Long id) {
        return this.wishlistRepository.deleteWishlistById(id);
    }

    @Override
    public boolean deleteAll() {
        return this.wishlistRepository.deleteAllWishlists();
    }
}
