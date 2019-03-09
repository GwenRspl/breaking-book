package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAllBooks();
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public boolean create(Book book) {
        return bookRepository.createBook(book);
    }

    @Override
    public boolean update(Long id, Book book) {
        return bookRepository.updateBook(id, book);
    }

    @Override
    public boolean delete(Long id) {
        return bookRepository.deleteBookById(id);
    }

    @Override
    public boolean deleteAll() {
        return bookRepository.deleteAllBooks();
    }
}
