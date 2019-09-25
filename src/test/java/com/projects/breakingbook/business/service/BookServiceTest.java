package com.projects.breakingbook.business.service;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.implementation.BookServiceImpl;
import com.projects.breakingbook.persistence.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Autowired
    BookServiceImpl bookService;

    @MockBean
    BookRepository bookRepository;

    private User user1;
    private User user2;
    private Book book1;
    private Book book2;
    private Book book3;
    private Friend friend1;

    @Before
    public void setUp() {
        this.user1 = User.builder()
                .id(1L)
                .username("user1")
                .build();

        this.user2 = User.builder()
                .id(2L)
                .username("user2")
                .build();

        this.book1 = Book.builder()
                .id(1L)
                .title("Snow White")
                .user(this.user1)
                .friend(null)
                .build();

        this.book2 = Book.builder()
                .id(2L)
                .title("Maleficent")
                .comment("Fancy comment")
                .user(this.user2)
                .friend(null)
                .build();

        this.book3 = Book.builder()
                .id(1L)
                .title("Coco")
                .user(this.user1)
                .friend(null)
                .build();

        this.friend1 = Friend.builder()
                .id(1L)
                .name("Tom")
                .build();
    }

    @Test
    public void listAll() {
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Arrays.asList(this.book1, this.book3));

        final List<Book> books = this.bookService.getAll(1L);
        assertThat(books, hasSize(2));
        assertThat(books.get(0).getTitle(), equalTo("Snow White"));
        assertThat(books.get(1).getTitle(), equalTo("Coco"));
    }

    @Test
    public void should_return_empty_array_when_no_books_lent() {
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Arrays.asList(this.book1, this.book3));

        final List<Book> books = this.bookService.getAllLentBooks(1L);
        assertThat(books, hasSize(0));
    }

    @Test
    public void should_return_lent_book() {
        this.book1.setFriend(this.friend1);
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Collections.singletonList(this.book1));

        final List<Book> books = this.bookService.getAllLentBooks(1L);
        assertThat(books, hasSize(1));
        assertThat(books.get(0).getTitle(), equalTo("Snow White"));
    }

    @Test
    public void should_return_true_when_updating_book_if_book_exists_in_db() {
        final Book newBook = Book.builder()
                .id(2L)
                .title("Sleeping Beauty")
                .build();

        when(this.bookRepository.findBookById(2L)).thenReturn(Optional.of(this.book2));
        when(this.bookRepository.updateBook(2L, newBook)).thenReturn(true);

        assertThat(this.bookService.update(2L, newBook)).isEqualTo(true);
    }

    @Test
    public void should_return_false_when_updating_book_if_book_does_not_exist_in_db() {
        final Book newBook = Book.builder()
                .id(5L)
                .title("Sleeping Beauty")
                .build();

        when(this.bookRepository.findBookById(5L)).thenReturn(Optional.empty());

        assertThat(this.bookService.update(5L, newBook)).isEqualTo(false);
    }
}
