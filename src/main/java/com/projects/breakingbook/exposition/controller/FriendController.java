package com.projects.breakingbook.exposition.controller;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.exposition.DTO.FriendDTO;
import com.projects.breakingbook.exposition.exception.FriendNotCreatedException;
import com.projects.breakingbook.exposition.exception.FriendNotUpdatedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "*")
public class FriendController {

    private final FriendService friendService;
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public FriendController(final FriendService friendService, final BookService bookService, final UserService userService, final ModelMapper modelMapper) {
        this.friendService = friendService;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<FriendDTO> getAll(@RequestParam final Long userId) {
        final List<Friend> friends = this.friendService.getAll(userId);
        return friends.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FriendDTO getOne(@PathVariable final Long id) {
        final Optional<Friend> optionalFriend = this.friendService.getOne(id);
        return optionalFriend.map(this::convertToDTO).orElse(null);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody final FriendDTO friendDTO) {
        final Long friendId;
        try {
            friendId = this.friendService.create(this.convertToEntity(friendDTO));
            if (friendId != null) {
                return new ResponseEntity<>(friendId, HttpStatus.OK);
            } else {
                throw new FriendNotCreatedException("Friend not created");
            }
        } catch (final ParseException | FriendNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final FriendDTO friendDTO) {
        final boolean result;
        try {
            result = this.friendService.update(id, this.convertToEntity(friendDTO));
            if (result) {
                return new ResponseEntity<>("Friend updated successfully", HttpStatus.OK);
            } else {
                throw new FriendNotUpdatedException("Book not updated");
            }
        } catch (final ParseException | FriendNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final boolean result;
        final Long bookId = this.friendService.getBorrowedBook(id);
        if (bookId == null) {
            result = this.friendService.delete(id);
        } else {
            this.bookService.updateFriend(bookId, null);
            this.bookService.toggleOwned(bookId);
            result = this.friendService.delete(id);
        }

        if (result) {
            return new ResponseEntity<>("friendId deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("friendId not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    // TODO : delete this, not very useful
    @DeleteMapping("")
    public ResponseEntity<?> deleteAll(final Long userId) {
        Long bookId;
        final List<Friend> friends = this.friendService.getAll(userId);
        boolean result;
        for (final Friend friend : friends) {
            bookId = this.friendService.getBorrowedBook(friend.getId());
            if (bookId == null) {
                result = this.friendService.delete(friend.getId());
            } else {
                this.bookService.updateFriend(bookId, null);
                this.bookService.toggleOwned(bookId);
                result = this.friendService.delete(friend.getId());
            }
            if (!result) {
                return new ResponseEntity<>("No friendId deleted", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("All friends deleted successfully", HttpStatus.OK);
    }

    private FriendDTO convertToDTO(final Friend friend) {
        final FriendDTO friendDTO = this.modelMapper.map(friend, FriendDTO.class);
        friendDTO.setUserId(friend.getUser().getId());
        if (friend.getHistory() != null) {
            final List<Long> history = friend.getHistory().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            friendDTO.setHistoryBookIds(history);
        }
        return friendDTO;
    }

    private Friend convertToEntity(final FriendDTO friendDTO) throws ParseException {
        final Friend friend = this.modelMapper.map(friendDTO, Friend.class);
        if (friendDTO.getUserId() != null) {
            final Optional<User> optionalUser = this.userService.getOne(friendDTO.getUserId());
            optionalUser.ifPresent(friend::setUser);
        }
        if (friendDTO.getHistoryBookIds() != null) {
            final List<Book> history = friendDTO.getHistoryBookIds().stream()
                    .map(id -> this.bookService.getOne(id))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            friend.setHistory(history);
        }
        return friend;
    }
}
