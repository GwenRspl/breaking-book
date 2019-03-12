package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.ReaderService;
import com.projects.breakingbook.persistence.entity.Reader;
import com.projects.breakingbook.persistence.repository.ReaderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReaderServiceImpl implements ReaderService {

    private ReaderRepository readerRepository;

    public ReaderServiceImpl(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Reader> getAll() {
        return this.readerRepository.findAllReaders();
    }

    @Override
    public Reader getOne(Long id) {
        return this.readerRepository.findReaderById(id);
    }

    @Override
    public boolean create(Reader reader) {
        return this.readerRepository.createReader(reader);
    }

    @Override
    public boolean update(Long id, Reader reader) {
        Reader originalReader = this.readerRepository.findReaderById(id);
        if(reader.getName() == null) reader.setName(originalReader.getName());
        if(reader.getAvatar() == null) reader.setAvatar(originalReader.getAvatar());
        if(reader.getEmail() == null) reader.setEmail(originalReader.getEmail());
        if(reader.getPassword() == null) reader.setPassword(originalReader.getPassword());
        return this.readerRepository.updateReader(id, reader);
    }

    @Override
    public boolean delete(Long id) {
        return this.readerRepository.deleteReaderById(id);
    }

    @Override
    public boolean deleteAll() {
        return this.readerRepository.deleteAllReaders();
    }
}
