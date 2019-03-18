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
        return this.readerService.getAll();
    }

    @GetMapping("/{id}")
    public Reader getOne(@PathVariable final Long id) {

        return this.readerService.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final Reader reader) {
        boolean result = this.readerService.create(reader);
        if(result) {
            return new ResponseEntity<>("Reader successfully created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reader not created", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final Reader reader) {
        boolean result = this.readerService.update(id, reader);
        if(result) {
            return new ResponseEntity<>("Reader updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reader not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.readerService.delete(id);
        if(result) {
            return new ResponseEntity<>("Reader deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reader not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.readerService.deleteAll();
        if(result) {
            return new ResponseEntity<>("All readers deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No reader deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
