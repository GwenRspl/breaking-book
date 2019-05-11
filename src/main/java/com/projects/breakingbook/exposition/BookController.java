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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private BookService bookService;

    private FriendService friendService;

    private UserService userService;

    private ModelMapper modelMapper;

    public BookController(BookService bookService, FriendService friendService, UserService userService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.friendService = friendService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<BookDTO> getAll(@RequestParam final Long userId) {
        List<Book> books = this.bookService.getAll(userId);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable final Long id) {
        Optional<Book> optionalBook = this.bookService.getOne(id);
        return optionalBook.map(this::convertToDTO).orElse(null);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final BookDTO bookDto) {
        boolean result = false;
        try {
            result = this.bookService.create(convertToEntity(bookDto));
            if(result) {
                return new ResponseEntity<>("Book successfully created", HttpStatus.OK);
            } else {
                throw new BookNotCreatedException("Book not created");
            }
        } catch (ParseException | BookNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final BookDTO bookDTO) {
        boolean result = false;
        try {
            result = this.bookService.update(id, convertToEntity(bookDTO));
            if(result) {
                return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
            } else {
                throw new BookNotUpdatedException("Book not updated");
            }
        } catch (ParseException | BookNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.bookService.delete(id);
        if(result) {
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.bookService.deleteAll();
        if(result) {
            return new ResponseEntity<>("All books deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No book deleted", HttpStatus.BAD_REQUEST);
        }
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        bookDTO.setFriendId(book.getFriend().getId());
        bookDTO.setUserId(book.getUser().getId());
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) throws ParseException {
        Book book = modelMapper.map(bookDTO, Book.class);
        if(bookDTO.getFriendId() != null) {
            Optional<Friend> optionalFriend = this.friendService.getOne(bookDTO.getFriendId());
            if (optionalFriend.isPresent()) {
                book.setFriend(optionalFriend.get());
            } else {
                book.setFriend(null);
            }
        }
        if(bookDTO.getUserId() != null) {
            Optional<User> optionalUser = this.userService.getOne(bookDTO.getUserId());
            optionalUser.ifPresent(book::setUser);
        }
        return book;
    }
}
