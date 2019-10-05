package com.projects.breakingbook.exposition.controller;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.BookStatus;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.RoleName;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.exposition.DTO.FriendDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FriendControllerTest {

    @Autowired
    private FriendController friendController;

    @MockBean
    private FriendService friendService;

    private User winnie;
    private Friend cocoLapin;
    private Friend bourriquet;
    private Friend porcinet;
    private FriendDTO cocoLapinDTO;
    private FriendDTO bourriquetDTO;
    private FriendDTO porcinetDTO;
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

        this.cocoLapinDTO = FriendDTO.builder()
                .id(1L)
                .name("cocoLapin")
                .userId(1L)
                .build();

        this.bourriquetDTO = FriendDTO.builder()
                .id(2L)
                .name("Bourriquet")
                .userId(1L)
                .build();

        this.porcinetDTO = FriendDTO.builder()
                .name("Porcinet")
                .userId(1L)
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

    }

    @Test
    public void should_return_all_friends() {
        when(this.friendService.getAll(this.winnie.getId())).thenReturn(Arrays.asList(this.cocoLapin, this.bourriquet, this.porcinet));
        final List<FriendDTO> expectedFriends = Arrays.asList(this.cocoLapinDTO, this.bourriquetDTO, this.porcinetDTO);
        final List<FriendDTO> actualFriends = this.friendController.getAll(this.winnie.getId());
        assertThat(actualFriends).isEqualTo(expectedFriends);
    }

    @Test
    public void should_return_one_friend() {
        when(this.friendService.getOne(this.cocoLapin.getId())).thenReturn(Optional.of(this.cocoLapin));
        final FriendDTO expectedFriend = this.cocoLapinDTO;
        final FriendDTO actualFriend = this.friendController.getOne(this.cocoLapin.getId());
        assertThat(actualFriend).isEqualTo(expectedFriend);
    }

    @Test
    public void should_return_httpStatusOK_when_friend_is_created() throws ParseException {
        when(this.friendService.create(this.friendController.convertToEntity(this.porcinetDTO))).thenReturn(3L);
        final ResponseEntity<?> expected = new ResponseEntity<>(3L, HttpStatus.OK);
        final ResponseEntity<?> actual = this.friendController.create(this.porcinetDTO);
        assertThat(actual).isEqualTo(expected);
    }

}
