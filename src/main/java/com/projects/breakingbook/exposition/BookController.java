package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.persistence.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public List<Book> getAll() {
        return this.bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getOne(@PathVariable final Long id) {
        return this.bookService.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final Book book) {
        boolean result = this.bookService.create(book);
        if(result) {
            return new ResponseEntity<>("Book successfully created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not created", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final Book book) {
        boolean result = this.bookService.update(id, book);
        if(result) {
            return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not updated", HttpStatus.BAD_REQUEST);
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
}
