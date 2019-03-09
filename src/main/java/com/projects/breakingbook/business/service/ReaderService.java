package com.projects.breakingbook.business.service;

import com.projects.breakingbook.persistence.entity.Reader;

import java.util.List;

public interface ReaderService {

    List<Reader> getAll();
    Reader getOne(final Long id);
    Reader create();
    Reader update(final Long id);
    Boolean delete(final Long id);
    Boolean deleteAll();
}
