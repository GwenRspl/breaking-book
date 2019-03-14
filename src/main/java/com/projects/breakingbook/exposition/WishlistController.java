package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.WishlistService;
import com.projects.breakingbook.persistence.entity.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
@CrossOrigin(origins = "*")
public class WishlistController {

    private WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("")
    public List<Wishlist> getAll() {
        return this.wishlistService.getAll();
    }

    @GetMapping("/{id}")
    public Wishlist getOne(@PathVariable final Long id) {
        return this.wishlistService.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final Wishlist wishlist) {
        boolean result = this.wishlistService.create(wishlist);
        if(result) {
            return new ResponseEntity<>("wishlist successfully created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("wishlist not created", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final Wishlist wishlist) {
        boolean result = this.wishlistService.update(id, wishlist);
        if(result) {
            return new ResponseEntity<>("wishlist updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("wishlist not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.wishlistService.delete(id);
        if(result) {
            return new ResponseEntity<>("wishlist deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("wishlist not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.wishlistService.deleteAll();
        if(result) {
            return new ResponseEntity<>("All wishlists deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No wishlist deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
