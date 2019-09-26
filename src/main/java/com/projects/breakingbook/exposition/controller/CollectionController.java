package com.projects.breakingbook.exposition.controller;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Collection;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.CollectionService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.exposition.DTO.CollectionDTO;
import com.projects.breakingbook.exposition.exception.CollectionNotCreatedException;
import com.projects.breakingbook.exposition.exception.CollectionNotUpdatedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "${breaking-book.app.client}")
public class CollectionController {
    private final CollectionService collectionService;
    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final UserService userService;

    public CollectionController(final CollectionService collectionService, final ModelMapper modelMapper, final BookService bookService, final UserService userService) {
        this.collectionService = collectionService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<CollectionDTO> getAll(@RequestParam final Long userId) {
        final List<Collection> collections = this.collectionService.getAll(userId);
        return collections.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CollectionDTO getOne(@PathVariable final Long id) {
        final Optional<Collection> optionalCollection = this.collectionService.getOne(id);
        return optionalCollection.map(this::convertToDTO).orElse(null);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final CollectionDTO collectionDTO) {
        boolean result = false;
        try {
            result = this.collectionService.create(this.convertToEntity(collectionDTO));
            if (result) {
                return new ResponseEntity<>("Collection successfully created", HttpStatus.OK);
            } else {
                throw new CollectionNotCreatedException("Collection not created");
            }
        } catch (final ParseException | CollectionNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addBookToCollection(@PathVariable final Long id, @RequestBody final Long bookId) {
        final boolean result = this.collectionService.addBookToCollection(id, bookId);
        if (result) {
            return new ResponseEntity<>("Book successfully added to collection.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not added to collection", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final CollectionDTO collectionDTO) {
        boolean result = false;
        try {
            result = this.collectionService.update(id, this.convertToEntity(collectionDTO));
            if (result) {
                return new ResponseEntity<>("Collection updated successfully", HttpStatus.OK);
            } else {
                throw new CollectionNotUpdatedException("Collection not updated");
            }
        } catch (final ParseException | CollectionNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeBookFromCollection(@PathVariable final Long id, @RequestParam final Long bookId) {
        final boolean result = this.collectionService.removeBookFromCollection(id, bookId);
        if (result) {
            return new ResponseEntity<>("Book successfully removed from collection", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not removed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final boolean result = this.collectionService.delete(id);
        if (result) {
            return new ResponseEntity<>("Collection deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Collection not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    private CollectionDTO convertToDTO(final Collection collection) {
        final CollectionDTO collectionDTO = this.modelMapper.map(collection, CollectionDTO.class);
        if (collection.getUser() != null) {
            collectionDTO.setUserId(collection.getUser().getId());
        }
        if (collection.getBooks() != null) {
            final List<Long> booksIds = collection.getBooks()
                    .stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            collectionDTO.setBooksIds(booksIds);
        }
        return collectionDTO;
    }

    private Collection convertToEntity(final CollectionDTO collectionDTO) throws ParseException {
        final Collection collection = this.modelMapper.map(collectionDTO, Collection.class);
        if (collectionDTO.getUserId() != null) {
            final Optional<User> optionalUser = this.userService.getOne(collectionDTO.getUserId());
            optionalUser.ifPresent(collection::setUser);
        }

        if (collectionDTO.getBooksIds() != null) {
            final List<Book> books = collectionDTO.getBooksIds().stream()
                    .map(id -> this.bookService.getOne(id))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            collection.setBooks(books);
        }
        return collection;
    }
}
