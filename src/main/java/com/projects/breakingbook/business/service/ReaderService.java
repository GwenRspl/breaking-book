package com.projects.breakingbook.business.service;

import com.projects.breakingbook.persistence.entity.Reader;

import java.util.List;

public interface ReaderService {

    List<Reader> getAll();
    Reader getOne(final Long id);
    boolean create(final Reader reader);
    boolean update(final Long id, final Reader reader);
    boolean delete(final Long id);
    boolean deleteAll();
}
