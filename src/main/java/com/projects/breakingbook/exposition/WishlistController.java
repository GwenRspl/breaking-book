package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.business.service.WishlistService;
import com.projects.breakingbook.exception.WishlistNotCreatedException;
import com.projects.breakingbook.exception.WishlistNotUpdatedException;
import com.projects.breakingbook.exposition.DTO.WishlistDTO;
import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.User;
import com.projects.breakingbook.persistence.entity.Wishlist;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishlists")
@CrossOrigin(origins = "*")
public class WishlistController {

    private final WishlistService wishlistService;
    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final UserService userService;

    public WishlistController(final WishlistService wishlistService, final ModelMapper modelMapper, final BookService bookService, final UserService userService) {
        this.wishlistService = wishlistService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<WishlistDTO> getAll(@RequestParam final Long userId) {
        final List<Wishlist> wishlists = this.wishlistService.getAll(userId);
        return wishlists.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WishlistDTO getOne(@PathVariable final Long id) {
        final Optional<Wishlist> optionalWishlist = this.wishlistService.getOne(id);
        return optionalWishlist.map(this::convertToDTO).orElse(null);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final WishlistDTO wishlistDTO) {
        boolean result = false;
        try {
            result = this.wishlistService.create(this.convertToEntity(wishlistDTO));
            if (result) {
                return new ResponseEntity<>("Wishlist successfully created", HttpStatus.OK);
            } else {
                throw new WishlistNotCreatedException("Wishlist not created");
            }
        } catch (final ParseException | WishlistNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final WishlistDTO wishlistDTO) {
        boolean result = false;
        try {
            result = this.wishlistService.update(id, this.convertToEntity(wishlistDTO));
            if (result) {
                return new ResponseEntity<>("Wishlist updated successfully", HttpStatus.OK);
            } else {
                throw new WishlistNotUpdatedException("Wishlist not updated");
            }
        } catch (final ParseException | WishlistNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final boolean result = this.wishlistService.delete(id);
        if (result) {
            return new ResponseEntity<>("wishlist deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("wishlist not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        final boolean result = this.wishlistService.deleteAll();
        if (result) {
            return new ResponseEntity<>("All wishlists deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No wishlist deleted", HttpStatus.BAD_REQUEST);
        }
    }

    private WishlistDTO convertToDTO(final Wishlist wishlist) {
        final WishlistDTO wishlistDTO = this.modelMapper.map(wishlist, WishlistDTO.class);
        if (wishlist.getUser() != null) {
            wishlistDTO.setUserId(wishlist.getUser().getId());
        }
        if (wishlist.getBooks() != null) {
            final List<Long> booksIds = wishlist.getBooks()
                    .stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            wishlistDTO.setBooksIds(booksIds);
        }
        return wishlistDTO;
    }

    private Wishlist convertToEntity(final WishlistDTO wishlistDTO) throws ParseException {
        final Wishlist wishlist = this.modelMapper.map(wishlistDTO, Wishlist.class);
        if (wishlistDTO.getUserId() != null) {
            final Optional<User> optionalUser = this.userService.getOne(wishlistDTO.getUserId());
            optionalUser.ifPresent(wishlist::setUser);
        }
        if (wishlistDTO.getBooksIds() != null) {
            final List<Book> books = wishlistDTO.getBooksIds().stream()
                    .map(id -> this.bookService.getOne(id))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            wishlist.setBooks(books);
        }
        return wishlist;
    }
}
