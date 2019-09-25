package com.projects.breakingbook.business.service;

import com.projects.breakingbook.business.entity.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface CollectionService {
    List<Collection> getAll(final Long userId);

    Optional<Collection> getOne(final Long id);

    boolean create(final Collection collection);

    boolean update(final Long id, final Collection collection);

    boolean delete(final Long id);

    boolean deleteAll();

    boolean addBookToCollection(final Long id, final Long bookId);

    boolean removeBookFromCollection(final Long id, final Long bookId);
}
