package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.utils.ServiceUtils;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll(final Long userId) {
        return this.bookRepository.findAllBooks(userId);
    }

    @Override
    public List<Book> getAllLentBooks(final Long userId) {
        return this.bookRepository
                .findAllBooks(userId)
                .stream()
                .filter(book -> {
                    if (book.getFriend() == null) {
                        return false;
                    }
                    if (book.getFriend().getId() == null || book.getFriend().getId() == 0) {
                        return true;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> getOne(final Long id) {
        return this.bookRepository.findBookById(id);
    }

    @Override
    public Long create(final Book book) {
        return this.bookRepository.createBook(book);
    }

    @Override
    public boolean update(final Long id, Book book) {
        final Optional<Book> optionalBook = this.bookRepository.findBookById(id);
        Book originalBook = null;
        book.setId(id);
        if (!optionalBook.isPresent()) {
            return false;
        }
        originalBook = optionalBook.get();
        book = ServiceUtils.generateBooksAttributes(book, originalBook);
        return this.bookRepository.updateBook(id, book);
    }

    @Override
    public boolean delete(final Long id) {
        return this.bookRepository.deleteBookById(id);
    }

    @Override
    public boolean deleteAll() {
        return this.bookRepository.deleteAllBooks();
    }

    @Override
    public boolean toggleOwned(final Long id) {
        return this.bookRepository.toggleOwned(id);
    }

    @Override
    public boolean updateFriend(final Long bookId, final Long friendId) {
        return this.bookRepository.updateFriend(bookId, friendId);
    }
}
