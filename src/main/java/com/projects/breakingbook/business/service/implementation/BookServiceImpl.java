package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Optional<Book> getOne(Long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public boolean create(Book book) {
        return bookRepository.createBook(book);
    }

    @Override
    public boolean update(Long id, Book book) {
        Optional<Book> optionalBook = this.bookRepository.findBookById(id);
        Book originalBook = null;
        if (optionalBook.isPresent())  optionalBook.get();
        if(book.getTitle() == null) book.setTitle(originalBook.getTitle());
        if(book.getAuthors() == null) book.setAuthors(originalBook.getAuthors());
        if(book.getIsbn() == null) book.setIsbn(originalBook.getIsbn());
        if(book.getImage() == null) book.setImage(originalBook.getImage());
        if(book.getLanguage() == null) book.setLanguage(originalBook.getLanguage());
        if(book.getPublisher() == null) book.setPublisher(originalBook.getPublisher());
        if(book.getDatePublished() == null) book.setDatePublished(originalBook.getDatePublished());
        if(book.getPages() == 0) book.setPages(originalBook.getPages());
        if(book.getSynopsis() == null) book.setSynopsis(originalBook.getSynopsis());
        if(book.getReader() == null) book.setReader(originalBook.getReader());
        if(book.getFriend() == null) book.setFriend(originalBook.getFriend());
        book.setOwned(originalBook.isOwned());
        book.setRating(originalBook.getRating());
        book.setComment(originalBook.getComment());
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
