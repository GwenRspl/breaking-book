package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.CollectionService;
import com.projects.breakingbook.persistence.entity.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
public class CollectionController  {
    private CollectionService CollectionService;

    public CollectionController(CollectionService CollectionService) {
        this.CollectionService = CollectionService;
    }

    @GetMapping("")
    public List<Collection> getAll() {
        return this.CollectionService.getAll();
    }

    @GetMapping("/{id}")
    public Collection getOne(@PathVariable final Long id) {
        return this.CollectionService.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final Collection Collection) {
        boolean result = this.CollectionService.create(Collection);
        if(result) {
            return new ResponseEntity<>("Collection successfully created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Collection not created", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final Collection Collection) {
        boolean result = this.CollectionService.update(id, Collection);
        if(result) {
            return new ResponseEntity<>("Collection updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Collection not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.CollectionService.delete(id);
        if(result) {
            return new ResponseEntity<>("Collection deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Collection not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.CollectionService.deleteAll();
        if(result) {
            return new ResponseEntity<>("All Collections deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Collection deleted", HttpStatus.BAD_REQUEST);
        }

    }
}
