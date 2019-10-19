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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "${breaking-book.app.client}")
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
        final List<Long> booksIds = this.friendService.getBorrowedBook(id);
        if (!booksIds.isEmpty()) {
            booksIds.forEach(bookId -> {
                this.bookService.updateFriend(bookId, null);
                this.bookService.toggleOwned(bookId);
            });
        }
        result = this.friendService.delete(id);

        if (result) {
            return new ResponseEntity<>("friendId deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("friendId not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    FriendDTO convertToDTO(final Friend friend) {
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

    Friend convertToEntity(final FriendDTO friendDTO) throws ParseException {
        final Friend friend = this.modelMapper.map(friendDTO, Friend.class);
        if (friendDTO.getUserId() != null) {
            final Optional<User> optionalUser = this.userService.getOne(friendDTO.getUserId());
            optionalUser.ifPresent(friend::setUser);
        }
        if (friendDTO.getHistoryBookIds() != null) {
            final List<Book> history = friendDTO.getHistoryBookIds().stream()
                    .map(this.bookService::getOne)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            friend.setHistory(history);
        }
        return friend;
    }
}
