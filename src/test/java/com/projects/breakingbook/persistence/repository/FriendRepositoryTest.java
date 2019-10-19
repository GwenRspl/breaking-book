package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.persistence.repository.config.PostgresqlContainerTest;
import com.projects.breakingbook.persistence.repository.utils.TestUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@Sql({"/schema-test.sql"})
@SpringBootTest
@RunWith(SpringRunner.class)
public class FriendRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainerTest.getInstance();
    private final String UPDATED_NAME = "Porcinette";
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    private User winnie;
    private Friend cocoLapin;
    private Friend bourriquet;
    private Friend porcinet;
    private Book lesAventures;
    private Book etlEphelant;
    private Book etSesAmis;

    @Before
    public void setUp() {
        this.winnie = TestUtils.winnie;
        this.cocoLapin = TestUtils.cocoLapin;
        this.bourriquet = TestUtils.bourriquet;
        this.porcinet = TestUtils.porcinet;
        this.lesAventures = TestUtils.lesAventures;
        this.etlEphelant = TestUtils.etlEphelant;
        this.etSesAmis = TestUtils.etSesAmis;

        this.userRepository.createUser(this.winnie);
        this.friendRepository.createFriend(this.cocoLapin);
        this.friendRepository.createFriend(this.bourriquet);
        this.bookRepository.createBook(this.lesAventures);
        this.bookRepository.createBook(this.etlEphelant);
        this.bookRepository.createBook(this.etSesAmis);
    }

    @Test
    @Transactional
    public void should_create_friend() {
        final Long id = this.friendRepository.createFriend(this.porcinet);
        assertNotNull(id);
        final Optional<Friend> actualFriend = this.friendRepository.findFriendById(id);
        assertThat(actualFriend.isPresent(), equalTo(true));
        assertThat(actualFriend.get().getName(), equalTo(this.porcinet.getName()));
    }

    @Test
    @Transactional
    public void should_correctly_update_friend() {
        final Long id = this.friendRepository.createFriend(this.porcinet);
        this.porcinet.setName(this.UPDATED_NAME);
        this.friendRepository.updateFriend(id, this.porcinet);
        final Optional<Friend> actualFriend = this.friendRepository.findFriendById(id);
        assertThat(actualFriend.isPresent(), equalTo(true));
        assertThat(actualFriend.get().getName(), equalTo(this.UPDATED_NAME));
    }

    @Test
    @Transactional
    public void should_delete_friend() {
        final Long id = this.friendRepository.createFriend(this.porcinet);
        this.friendRepository.deleteFriendById(id);
        final Optional<Friend> actualFriend = this.friendRepository.findFriendById(id);
        assertThat(actualFriend.isPresent(), equalTo(false));
    }

    @Test
    @Transactional
    public void should_return_all_friends() {
        final List<Friend> expectedFriends = Arrays.asList(this.cocoLapin, this.bourriquet);
        final List<Friend> actualFriends = this.friendRepository.findAllFriends(this.winnie.getId());
        assertThat(actualFriends, hasSize(expectedFriends.size()));
    }

    @Test
    @Transactional
    public void should_list_all_lent_books_by_friend_id() {
        final List<Long> expectedLentBooks = Arrays.asList(this.lesAventures.getId(), this.etlEphelant.getId());
        final List<Long> actualLentBooks = this.friendRepository.getBorrowedBook(this.cocoLapin.getId());
        System.out.println(actualLentBooks.toString());
        assertThat(actualLentBooks.size(), equalTo(expectedLentBooks.size()));
    }

    @Test
    @Transactional
    public void should_return_true_when_book_added_to_history() {
        final boolean expected = true;
        final boolean actual = this.friendRepository.addBookToHistory(this.etSesAmis.getId(), this.bourriquet.getId());
        assertThat(actual, equalTo(expected));
    }
}
