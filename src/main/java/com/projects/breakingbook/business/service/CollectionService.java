package com.projects.breakingbook.business.service;

import com.projects.breakingbook.persistence.entity.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CollectionService {
    List<Collection> getAll();
    Collection getOne(final Long id);
    boolean create(final Collection collection);
    boolean update(final Long id, final Collection collection);
    boolean delete(final Long id);
    boolean deleteAll();
}
