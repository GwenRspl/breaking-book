package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Reader;

import java.util.List;

public interface ReaderRepository {

    List<Reader> findAllReaders();
    boolean createReader(Reader reader);
    Reader findReaderById(Long id);
    boolean deleteReaderById(Long id);
    boolean deleteAllReaders();
    boolean updateReader(Long id, Reader reader);
}
