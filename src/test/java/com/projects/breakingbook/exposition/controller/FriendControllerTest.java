package com.projects.breakingbook.exposition.controller;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.FriendService;
import com.projects.breakingbook.exposition.DTO.FriendDTO;
import com.projects.breakingbook.persistence.repository.utils.TestUtils;
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
        this.winnie = TestUtils.winnie;

        this.cocoLapin = TestUtils.cocoLapin;

        this.bourriquet = TestUtils.bourriquet;

        this.porcinet = TestUtils.porcinet;

        this.cocoLapinDTO = TestUtils.cocoLapinDTO;

        this.bourriquetDTO = TestUtils.bourriquetDTO;

        this.porcinetDTO = TestUtils.porcinetDTO;

        this.lesAventures = TestUtils.lesAventures;

        this.etlEphelant = TestUtils.etlEphelant;

        this.etSesAmis = TestUtils.etSesAmis;
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

    @Test
    public void should_return_httpStatusOK_when_friend_is_updated() throws ParseException {
        final FriendDTO porcinetteDTO = FriendDTO.builder()
                .id(3L)
                .name("Porcinette")
                .build();
        when(this.friendService.update(this.porcinetDTO.getId(), this.friendController.convertToEntity(porcinetteDTO))).thenReturn(true);
        final ResponseEntity<?> expected = new ResponseEntity<>("Friend updated successfully", HttpStatus.OK);
        final ResponseEntity<?> actual = this.friendController.update(this.porcinetDTO.getId(), porcinetteDTO);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void should_return_friendNotUpdatedException_when_friend_is_not_updated() throws ParseException {
        final FriendDTO porcinetteDTO = FriendDTO.builder()
                .id(3L)
                .name("Porcinette")
                .build();
        when(this.friendService.update(this.porcinetDTO.getId(), this.friendController.convertToEntity(porcinetteDTO))).thenReturn(false);
        final ResponseEntity<?> expected = new ResponseEntity<>("Book not updated", HttpStatus.BAD_REQUEST);
        final ResponseEntity<?> actual = this.friendController.update(this.porcinetDTO.getId(), porcinetteDTO);
        assertThat(actual).isEqualTo(expected);
    }


}
