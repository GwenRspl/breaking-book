package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.CollectionService;
import com.projects.breakingbook.exception.BookNotUpdatedException;
import com.projects.breakingbook.exception.CollectionNotCreatedException;
import com.projects.breakingbook.exception.CollectionNotUpdatedException;
import com.projects.breakingbook.exposition.DTO.CollectionDTO;
import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Collection;
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
@CrossOrigin(origins = "*")
public class CollectionController {
    private CollectionService collectionService;
    private ModelMapper modelMapper;
    private BookService bookService;

    public CollectionController(CollectionService collectionService, ModelMapper modelMapper, BookService bookService) {
        this.collectionService = collectionService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @GetMapping("")
    public List<CollectionDTO> getAll() {
       List<Collection> collections = this.collectionService.getAll();
       return collections.stream()
               .map(this::convertToDTO)
               .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CollectionDTO getOne(@PathVariable final Long id) {
        Optional<Collection> optionalCollection = this.collectionService.getOne(id);
        return optionalCollection.map(this::convertToDTO).orElse(null);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final CollectionDTO collectionDTO) {
        boolean result = false;
        try {
            result = this.collectionService.create(convertToEntity(collectionDTO));
            if(result) {
                return new ResponseEntity<>("Collection successfully created", HttpStatus.OK);
            } else {
                throw new CollectionNotCreatedException("Collection not created");
            }
        } catch (ParseException | CollectionNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final CollectionDTO collectionDTO) {
        boolean result = false;
        try {
            result = this.collectionService.update(id, convertToEntity(collectionDTO));
            if(result) {
                return new ResponseEntity<>("Collection updated successfully", HttpStatus.OK);
            } else {
                throw new CollectionNotUpdatedException("Collection not updated");
            }
        } catch (ParseException | CollectionNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.collectionService.delete(id);
        if (result) {
            return new ResponseEntity<>("Collection deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Collection not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.collectionService.deleteAll();
        if (result) {
            return new ResponseEntity<>("All Collections deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Collection deleted", HttpStatus.BAD_REQUEST);
        }
    }

    private CollectionDTO convertToDTO(Collection collection) {
        CollectionDTO collectionDTO = modelMapper.map(collection, CollectionDTO.class);
        if(collection.getBooks() != null) {
            List<Long> booksIds = collection.getBooks()
                    .stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            collectionDTO.setBooksIds(booksIds);
        }
        return collectionDTO;
    }

    private Collection convertToEntity(CollectionDTO collectionDTO) throws ParseException {
        Collection collection = modelMapper.map(collectionDTO, Collection.class);
        if(collectionDTO.getBooksIds() != null) {
            List<Book> books = collectionDTO.getBooksIds().stream()
                    .map(id -> this.bookService.getOne(id))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            collection.setBooks(books);
        }
        return collection;
    }
}
