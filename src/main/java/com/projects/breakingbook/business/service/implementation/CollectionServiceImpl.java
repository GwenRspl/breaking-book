package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.entity.Collection;
import com.projects.breakingbook.business.service.CollectionService;
import com.projects.breakingbook.persistence.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    public CollectionServiceImpl(final CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Override
    public List<Collection> getAll(final Long userId) {
        return this.collectionRepository.findAllCollections(userId);
    }

    @Override
    public boolean create(final Collection collection) {
        return this.collectionRepository.createCollection(collection);
    }

    @Override
    public Optional<Collection> getOne(final Long id) {
        return this.collectionRepository.findCollectionById(id);
    }

    @Override
    public boolean delete(final Long id) {
        return this.collectionRepository.deleteCollectionById(id);
    }

    @Override
    public boolean deleteAll() {
        return this.collectionRepository.deleteAllCollections();
    }

    @Override
    public boolean addBookToCollection(final Long id, final Long bookId) {
        return this.collectionRepository.addBookToCollection(id, bookId);
    }

    @Override
    public boolean removeBookFromCollection(final Long id, final Long bookId) {
        return this.collectionRepository.removeBookFromCollection(id, bookId);
    }

    @Override
    public boolean update(final Long id, final Collection collection) {
        return this.collectionRepository.updateCollection(id, collection);
    }
}
