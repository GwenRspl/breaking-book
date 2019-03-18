package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.persistence.entity.Friend;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "*")
public class FriendController {

    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("")
    public List<Friend> getAll() {
        return this.friendService.getAll();
    }

    @GetMapping("/{id}")
    public Friend getOne(@PathVariable final Long id) {
        return this.friendService.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final Friend friend) {
        boolean result = this.friendService.create(friend);
        if(result) {
            return new ResponseEntity<>("friend successfully created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("friend not created", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final Friend friend) {
        boolean result = this.friendService.update(id, friend);
        if(result) {
            return new ResponseEntity<>("friend updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("friend not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.friendService.delete(id);
        if(result) {
            return new ResponseEntity<>("friend deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("friend not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.friendService.deleteAll();
        if(result) {
            return new ResponseEntity<>("All friends deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No friend deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
