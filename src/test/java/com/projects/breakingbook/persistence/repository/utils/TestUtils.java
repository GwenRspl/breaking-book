package com.projects.breakingbook.persistence.repository.utils;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.BookStatus;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.RoleName;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.exposition.DTO.FriendDTO;

import java.util.Collections;

public class TestUtils {

    public static final User winnie = User.builder()
            .id(1L)
            .username("Winnie")
            .email("winnie@foretdesreves.bleus")
            .password("123456")
            .avatar("ourson")
            .role(RoleName.ROLE_USER)
            .build();

    public static final User jeanChristophe = User.builder()
            .id(2L)
            .username("Jean-Christophe")
            .email("jean-christophe@foretdesreves.bleus")
            .password("azerty")
            .avatar("gamin")
            .role(RoleName.ROLE_USER)
            .build();

    public static final Friend cocoLapin = Friend.builder()
            .id(1L)
            .name("cocoLapin")
            .user(winnie)
            .build();

    public static final Friend bourriquet = Friend.builder()
            .id(2L)
            .name("Bourriquet")
            .user(winnie)
            .build();

    public static final Friend porcinet = Friend.builder()
            .name("Porcinet")
            .user(winnie)
            .build();

    public static final Book lesAventures = Book.builder()
            .id(1L)
            .title("Les aventures de Winnie l'ourson")
            .authors(Collections.singletonList("Walt Disney"))
            .user(winnie)
            .friend(cocoLapin)
            .status(BookStatus.READ)
            .owned(true)
            .build();

    public static final Book etlEphelant = Book.builder()
            .id(2L)
            .title("Winnie l'Ourson et l'Ephélant")
            .comment("Un éléphant qui ne sait pas prononcer son nom")
            .authors(Collections.singletonList("Walt Disney"))
            .user(winnie)
            .friend(cocoLapin)
            .status(BookStatus.READ)
            .owned(true)
            .build();

    public static final Book etSesAmis = Book.builder()
            .id(3L)
            .title("Winnie et ses amis")
            .authors(Collections.singletonList("Walt Disney"))
            .user(winnie)
            .friend(null)
            .status(BookStatus.READ)
            .owned(true)
            .build();

    public static final FriendDTO cocoLapinDTO = FriendDTO.builder()
            .id(1L)
            .name("cocoLapin")
            .userId(1L)
            .build();

    public static final FriendDTO bourriquetDTO = FriendDTO.builder()
            .id(2L)
            .name("Bourriquet")
            .userId(1L)
            .build();

    public static final FriendDTO porcinetDTO = FriendDTO.builder()
            .name("Porcinet")
            .userId(1L)
            .build();
}
