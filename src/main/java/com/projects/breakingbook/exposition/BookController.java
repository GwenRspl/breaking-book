package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.exception.BookNotCreatedException;
import com.projects.breakingbook.exception.BookNotUpdatedException;
import com.projects.breakingbook.exposition.DTO.BookDTO;
import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
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

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable final Long id) {
        final Optional<Book> optionalBook = this.bookService.getOne(id);
        return optionalBook.map(this::convertToDTO).orElse(null);
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
        } catch (final ParseException | BookNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
        } catch (final ParseException | BookNotUpdatedException e) {
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

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        final boolean result = this.bookService.deleteAll();
        if (result) {
            return new ResponseEntity<>("All books deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No book deleted", HttpStatus.BAD_REQUEST);
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

    private Book convertToEntity(final BookDTO bookDTO) throws ParseException {
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
