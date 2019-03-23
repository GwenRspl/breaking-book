package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Collection;

import java.util.List;

public interface CollectionRepository {

    List<Collection> findAllCollections();
    boolean createCollection(Collection collection);
    Collection findCollectionById(Long id);
    boolean deleteCollectionById(Long id);
    boolean deleteAllCollections();
    boolean updateCollection(Long id, Collection collection);
}
