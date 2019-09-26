package com.projects.breakingbook.exposition.controller;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.exposition.DTO.BookDTO;
import com.projects.breakingbook.exposition.exception.BookNotCreatedException;
import com.projects.breakingbook.exposition.exception.BookNotUpdatedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "${breaking-book.app.client}")
public class BookController {

    private final BookService bookService;

    private final FriendService friendService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public BookController(final BookService bookService, final FriendService friendService, final UserService userService, final ModelMapper modelMapper) {
        this.bookService = bookService;
        this.friendService = friendService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<BookDTO> getAll(@RequestParam final Long userId) {
        final List<Book> books = this.bookService.getAll(userId);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/lent")
    public List<BookDTO> getAllLentBooks(@RequestParam final Long userId) {
        final List<Book> books = this.bookService.getAllLentBooks(userId);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final BookDTO bookDto) {
        final Long bookId;

        try {
            bookId = this.bookService.create(this.convertToEntity(bookDto));
            if (bookId != null) {
                return new ResponseEntity<>(bookId, HttpStatus.OK);
            } else {
                throw new BookNotCreatedException("Book not created");
            }
        } catch (final BookNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/lend/{bookId}")
    public ResponseEntity<?> lendBookToFriend(@PathVariable final Long bookId, @RequestBody final Long friendId) {

        boolean result;
        try {
            result = this.bookService.updateFriend(bookId, friendId);
            if (!result) {
                throw new BookNotUpdatedException("Book not updated");
            }
            result = this.friendService.addBookToHistory(friendId, bookId);
            if (result) {
                return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
            } else {
                throw new BookNotUpdatedException("Book not updated");
            }
        } catch (final BookNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-back/{bookId}")
    public ResponseEntity<?> getBackBookFromFriend(@PathVariable final Long bookId) {
        final boolean result;
        try {
            result = this.bookService.updateFriend(bookId, null);
            if (result) {
                return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
            } else {
                throw new BookNotUpdatedException("Book not updated");
            }
        } catch (final BookNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable final Long id) {
        final Optional<Book> optionalBook = this.bookService.getOne(id);
        return optionalBook.map(this::convertToDTO).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final BookDTO bookDTO) {

        final boolean result;
        try {
            result = this.bookService.update(id, this.convertToEntity(bookDTO));
            if (result) {
                return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
            } else {
                throw new BookNotUpdatedException("Book not updated");
            }
        } catch (final BookNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final boolean result = this.bookService.delete(id);
        if (result) {
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    private BookDTO convertToDTO(final Book book) {
        final BookDTO bookDTO = this.modelMapper.map(book, BookDTO.class);
        bookDTO.setUserId(book.getUser().getId());
        if (book.getFriend().getId() == 0) {
            bookDTO.setFriendId(null);
            return bookDTO;
        }
        bookDTO.setFriendId(book.getFriend().getId());
        return bookDTO;
    }

    private Book convertToEntity(final BookDTO bookDTO) {
        final Book book = this.modelMapper.map(bookDTO, Book.class);
        if (bookDTO.getFriendId() != null) {
            final Optional<Friend> optionalFriend = this.friendService.getOne(bookDTO.getFriendId());
            if (optionalFriend.isPresent()) {
                book.setFriend(optionalFriend.get());
            } else {
                book.setFriend(null);
            }
        }
        if (bookDTO.getUserId() != null) {
            final Optional<User> optionalUser = this.userService.getOne(bookDTO.getUserId());
            optionalUser.ifPresent(book::setUser);
        }
        return book;
    }
}
