package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.ReaderService;
import com.projects.breakingbook.persistence.entity.Reader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
@CrossOrigin(origins = "*")
public class ReaderController {

    private ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("")
    public List<Reader> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public Reader getOne(@PathVariable final Long id) {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final Reader reader) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final Reader reader) {
        return new ResponseEntity<>("Reader updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        return new ResponseEntity<>("Reader deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        return new ResponseEntity<>("All readers deleted successfully", HttpStatus.OK);
    }
}
