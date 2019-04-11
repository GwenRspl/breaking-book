package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.persistence.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable final Long id) {

        return this.userService.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final User user) {
        boolean result = this.userService.create(user);
        if(result) {
            return new ResponseEntity<>("User successfully created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final User user) {
        boolean result = this.userService.update(id, user);
        if(result) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.userService.delete(id);
        if(result) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.userService.deleteAll();
        if(result) {
            return new ResponseEntity<>("All users deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No user deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
