package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.BookStatus;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.RoleName;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.persistence.repository.config.PostgresqlContainerTest;
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
import java.util.Collections;
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
        this.winnie = User.builder()
                .username("Winnie")
                .email("winnie@foretdesreves.bleus")
                .password("123456")
                .avatar("ourson")
                .role(RoleName.ROLE_USER)
                .id(1L)
                .build();

        this.cocoLapin = Friend.builder()
                .id(1L)
                .name("cocoLapin")
                .user(this.winnie)
                .build();

        this.bourriquet = Friend.builder()
                .id(2L)
                .name("Bourriquet")
                .user(this.winnie)
                .build();

        this.porcinet = Friend.builder()
                .name("Porcinet")
                .user(this.winnie)
                .build();

        this.lesAventures = Book.builder()
                .id(1L)
                .title("Les aventures de Winnie l'ourson")
                .authors(Collections.singletonList("Walt Disney"))
                .user(this.winnie)
                .friend(this.cocoLapin)
                .status(BookStatus.READ)
                .owned(true)
                .build();

        this.etlEphelant = Book.builder()
                .id(2L)
                .title("Winnie l'Ourson et l'Ephélant")
                .comment("Un éléphant qui ne sait pas prononcer son nom")
                .authors(Collections.singletonList("Walt Disney"))
                .user(this.winnie)
                .friend(this.cocoLapin)
                .status(BookStatus.READ)
                .owned(true)
                .build();

        this.etSesAmis = Book.builder()
                .id(3L)
                .title("Winnie et ses amis")
                .authors(Collections.singletonList("Walt Disney"))
                .user(this.winnie)
                .friend(null)
                .status(BookStatus.READ)
                .owned(true)
                .build();

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
        this.porcinet.setName("Porcinette");
        this.friendRepository.updateFriend(id, this.porcinet);
        final Optional<Friend> actualFriend = this.friendRepository.findFriendById(id);
        assertThat(actualFriend.isPresent(), equalTo(true));
        assertThat(actualFriend.get().getName(), equalTo("Porcinette"));
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
