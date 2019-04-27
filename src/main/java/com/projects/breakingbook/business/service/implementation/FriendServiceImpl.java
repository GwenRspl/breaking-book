package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.repository.FriendRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    private FriendRepository friendRepository;
    private BookService bookService;

    public FriendServiceImpl(FriendRepository friendRepository, BookService bookService) {
        this.friendRepository = friendRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Friend> getAll() {
        return this.friendRepository.findAllFriends();
    }

    @Override
    public Optional<Friend> getOne(Long id) {
        return this.friendRepository.findFriendById(id);
    }

    @Override
    public boolean create(Friend friend) {
        return this.friendRepository.createFriend(friend);
    }

    @Override
    public boolean update(Long id, Friend friend) {
        Optional<Friend> optionalFriend = this.friendRepository.findFriendById(id);
        if(optionalFriend.isPresent()) {
            if (friend.getName() == null) friend.setName(optionalFriend.get().getName());
            if (friend.getAvatar() == null) friend.setAvatar(optionalFriend.get().getAvatar());
            if (friend.getUser() == null) friend.setUser(optionalFriend.get().getUser());
            return this.friendRepository.updateFriend(id, friend);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
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
        for (Friend friend : friends) {
            boolean result = delete(friend.getId());
            System.out.println("FriendServiceImpl --> deleteAll --> forEach" + result);
            if (!result) {
                return false;
            }
        }
        return true;*/
return false;
    }

    @Override
    public Long getBorrowedBook(Long id) {
        return this.friendRepository.getBorrowedBook(id);
    }
}
