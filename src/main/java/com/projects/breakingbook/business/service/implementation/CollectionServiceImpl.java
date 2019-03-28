package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.CollectionService;
import com.projects.breakingbook.persistence.entity.Collection;
import com.projects.breakingbook.persistence.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private CollectionRepository collectionRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository) { this.collectionRepository = collectionRepository; }

    @Override
    public List<Collection> getAll() {
        return collectionRepository.findAllCollections();
    }

    @Override
    public boolean create(Collection collection) {
        return collectionRepository.createCollection(collection);
    }

    @Override
    public Optional<Collection> getOne(Long id) {
        return collectionRepository.findCollectionById(id);
    }

    @Override
    public boolean delete(Long id) {
        return collectionRepository.deleteCollectionById(id);
    }

    @Override
    public boolean deleteAll() {
        return collectionRepository.deleteAllCollections();
    }

    @Override
    public boolean update(Long id, Collection collection) {
        return collectionRepository.updateCollection(id, collection);
    }
}
