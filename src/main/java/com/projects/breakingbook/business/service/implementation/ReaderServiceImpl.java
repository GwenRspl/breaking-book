package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.ReaderService;
import com.projects.breakingbook.persistence.entity.Reader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReaderServiceImpl implements ReaderService {


    @Override
    public List<Reader> getAll() {
        return null;
    }

    @Override
    public Reader getOne(Long id) {
        return null;
    }

    @Override
    public Reader create() {
        return null;
    }

    @Override
    public Reader update(Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public Boolean deleteAll() {
        return null;
    }
}
