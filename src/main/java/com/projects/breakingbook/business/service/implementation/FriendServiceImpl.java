package com.projects.breakingbook.business.service.implementation;

import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.repository.FriendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    private FriendRepository friendRepository;

    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public List<Friend> getAll() {
        return this.friendRepository.findAllFriends();
    }

    @Override
    public Friend getOne(Long id) {
        return this.friendRepository.findFriendById(id);
    }

    @Override
    public boolean create(Friend friend) {
        return this.friendRepository.createFriend(friend);
    }

    @Override
    public boolean update(Long id, Friend friend) {
        Friend originalFriend = this.friendRepository.findFriendById(id);
        if(friend.getName() == null) friend.setName(originalFriend.getName());
        if(friend.getAvatar() == null) friend.setAvatar(originalFriend.getAvatar());
        if(friend.getReader() == null) friend.setReader(originalFriend.getReader());
        return this.friendRepository.updateFriend(id, friend);
    }

    @Override
    public boolean delete(Long id) {
        return this.friendRepository.deleteFriendById(id);
    }

    @Override
    public boolean deleteAll() {
        return this.friendRepository.deleteAllFriends();
    }
}
