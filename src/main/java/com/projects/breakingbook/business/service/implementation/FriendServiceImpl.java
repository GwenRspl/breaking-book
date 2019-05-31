package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.repository.FriendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final BookService bookService;

    public FriendServiceImpl(final FriendRepository friendRepository, final BookService bookService) {
        this.friendRepository = friendRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Friend> getAll(final Long userId) {
        return this.friendRepository.findAllFriends(userId);
    }

    @Override
    public Optional<Friend> getOne(final Long id) {
        return this.friendRepository.findFriendById(id);
    }

    @Override
    public Long create(final Friend friend) {
        return this.friendRepository.createFriend(friend);
    }

    @Override
    public boolean update(final Long id, final Friend friend) {
        final Optional<Friend> optionalFriend = this.friendRepository.findFriendById(id);
        if (optionalFriend.isPresent()) {
            if (friend.getName() == null) {
                friend.setName(optionalFriend.get().getName());
            }
            if (friend.getAvatar() == null) {
                friend.setAvatar(optionalFriend.get().getAvatar());
            }
            if (friend.getUser() == null) {
                friend.setUser(optionalFriend.get().getUser());
            }
            return this.friendRepository.updateFriend(id, friend);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(final Long id) {
      /*  Long bookId;
        try {
            bookId = this.friendRepository.getBorrowedBook(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            bookId = null;
        }
        if(bookId == null) {
            System.out.println("FriendServiceImpl --> delete --> bookId = null");
            return this.friendRepository.deleteFriendById(id);
        } else {
            System.out.println("FriendServiceImpl --> delete --> bookId !null");
            boolean resultUpdateFriend = this.bookService.updateFriend(bookId, null);
            boolean resultToggleOwned = this.bookService.toggleOwned(bookId);
            return this.friendRepository.deleteFriendById(id);
        }*/
        return this.friendRepository.deleteFriendById(id);
    }

    @Override
    public boolean deleteAll() {
/*        System.out.println("FriendServiceImpl --> deleteAll");
        List<Friend> friends = getAll();
        System.out.println("FriendServiceImpl --> deleteAll --> getAll: " + friends);
        for (Friend friendId : friends) {
            boolean result = delete(friendId.getId());
            System.out.println("FriendServiceImpl --> deleteAll --> forEach" + result);
            if (!result) {
                return false;
            }
        }
        return true;*/
        return false;
    }

    @Override
    public Long getBorrowedBook(final Long id) {
        return this.friendRepository.getBorrowedBook(id);
    }

    @Override
    public boolean addBookToHistory(final Long friendId, final Long bookId) {
        return this.friendRepository.addBookToHistory(bookId, friendId);
    }
}
