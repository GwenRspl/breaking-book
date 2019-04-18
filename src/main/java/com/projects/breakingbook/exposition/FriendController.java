package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.exception.FriendNotCreatedException;
import com.projects.breakingbook.exception.FriendNotUpdatedException;
import com.projects.breakingbook.exposition.DTO.FriendDTO;
import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "*")
public class FriendController {

    private FriendService friendService;
    private BookService bookService;
    private UserService userService;
    private ModelMapper modelMapper;

    public FriendController(FriendService friendService, BookService bookService, UserService userService, ModelMapper modelMapper) {
        this.friendService = friendService;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<FriendDTO> getAll() {
        List<Friend> friends = this.friendService.getAll();
        return friends.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FriendDTO getOne(@PathVariable final Long id) {
        Optional<Friend> optionalFriend = this.friendService.getOne(id);
        return optionalFriend.map(this::convertToDTO).orElse(null);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody final FriendDTO friendDTO) {
        boolean result = false;
        try {
            result = this.friendService.create(convertToEntity(friendDTO));
            if(result) {
                return new ResponseEntity<>("Friend successfully created", HttpStatus.OK);
            } else {
                throw new FriendNotCreatedException("Friend not created");
            }
        } catch (ParseException | FriendNotCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final FriendDTO friendDTO) {
        boolean result;
        try {
            result = this.friendService.update(id, convertToEntity(friendDTO));
            if(result) {
                return new ResponseEntity<>("Friend updated successfully", HttpStatus.OK);
            } else {
                throw new FriendNotUpdatedException("Book not updated");
            }
        } catch (ParseException | FriendNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result;
        Long bookId = this.friendService.getBorrowedBook(id);
        if (bookId == null) {
            result = this.friendService.delete(id);
        } else {
            this.bookService.updateFriend(bookId, null);
            this.bookService.toggleOwned(bookId);
            result = this.friendService.delete(id);
        }

        if (result) {
            return new ResponseEntity<>("friend deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("friend not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll() {
        Long bookId;
        List<Friend> friends = friendService.getAll();
        boolean result;
        for (Friend friend : friends) {
            bookId = this.friendService.getBorrowedBook(friend.getId());
            if (bookId == null) {
                result = this.friendService.delete(friend.getId());
            } else {
                this.bookService.updateFriend(bookId, null);
                this.bookService.toggleOwned(bookId);
                result = this.friendService.delete(friend.getId());
            }
            if (!result) {
                return new ResponseEntity<>("No friend deleted", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("All friends deleted successfully", HttpStatus.OK);
    }

    private FriendDTO convertToDTO(Friend friend) {
        FriendDTO friendDTO = modelMapper.map(friend, FriendDTO.class);
        friendDTO.setUserId(friend.getUser().getId());
        if(friend.getHistory() != null) {
            List<Long> history = friend.getHistory().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            friendDTO.setHistoryBookIds(history);
        }
        return friendDTO;
    }

    private Friend convertToEntity(FriendDTO friendDTO) throws ParseException {
        Friend friend = modelMapper.map(friendDTO, Friend.class);
        if(friendDTO.getUserId() != null) {
            Optional<User> optionalUser = this.userService.getOne(friendDTO.getUserId());
            optionalUser.ifPresent(friend::setUser);
        }
        if(friendDTO.getHistoryBookIds() != null) {
            List<Book> history = friendDTO.getHistoryBookIds().stream()
                    .map(id -> this.bookService.getOne(id))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            friend.setHistory(history);
        }
        return friend;
    }
}
