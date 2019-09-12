package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class FriendRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private FriendRepository friendRepository;
    private User user;
    private Friend alice;
    private Friend bob;

    @Before
    public void setUp() {
        this.user = User.builder()
                .username("user")
                .id(1L)
                .build();

        this.alice = Friend.builder()
                .name("Alice")
                .user(this.user)
                .build();

        this.bob = Friend.builder()
                .name("Bob")
                .user(this.user)
                .build();
    }

    @Test
    public void create_should_return_valid_friend() {
        final Long id = this.friendRepository.createFriend(this.alice);
        assertNotNull(id);
        final Optional<Friend> actualFriend = this.friendRepository.findFriendById(id);
        assertThat(actualFriend.get().getName(), equalTo(this.alice.getName()));
    }

    @Test
    public void update_should_update_correctly() {
        final Long id = this.friendRepository.createFriend(this.alice);
        this.alice.setName("Tom");
        this.friendRepository.updateFriend(id, this.alice);
        final Optional<Friend> actualFriend = this.friendRepository.findFriendById(id);
        assertThat(actualFriend.get().getName(), equalTo("Tom"));
    }

    @Test
    public void delete_by_id_should_delete_correct_friend() {
        final Long id = this.friendRepository.createFriend(this.bob);
        this.friendRepository.deleteFriendById(id);
        final Optional<Friend> actualFriend = this.friendRepository.findFriendById(id);
        assertThat(actualFriend.isPresent(), equalTo(false));
    }

    @Test
    public void list_all_should_return_all_friends() {
        final List<Friend> oldFriends = this.friendRepository.findAllFriends(this.user.getId());
        this.friendRepository.createFriend(this.bob);
        this.friendRepository.createFriend(this.alice);
        final List<Friend> friends = this.friendRepository.findAllFriends(this.user.getId());
        assertThat(friends, hasSize(oldFriends.size() + 2));
    }
}
