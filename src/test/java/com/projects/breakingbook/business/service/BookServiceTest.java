package com.projects.breakingbook.business.service;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.implementation.BookServiceImpl;
import com.projects.breakingbook.persistence.repository.BookRepository;
import com.projects.breakingbook.persistence.repository.utils.TestUtils;
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

    private User winnie;
    private User jeanChristophe;
    private Book lesAventures;
    private Book etlEphelant;
    private Book etSesAmis;
    private Friend cocoLapin;

    @Before
    public void setUp() {
        this.winnie = TestUtils.winnie;

        this.jeanChristophe = TestUtils.jeanChristophe;

        this.lesAventures = TestUtils.lesAventures;

        this.etlEphelant = TestUtils.etlEphelant;

        this.etSesAmis = TestUtils.etSesAmis;

        this.cocoLapin = TestUtils.cocoLapin;
    }

    @Test
    public void should_list_All_Books() {
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Arrays.asList(this.lesAventures, this.etSesAmis));

        final List<Book> books = this.bookService.getAll(1L);
        assertThat(books, hasSize(2));
        assertThat(books.get(0).getTitle(), equalTo("Les aventures de Winnie l'ourson"));
        assertThat(books.get(1).getTitle(), equalTo("Winnie et ses amis"));
    }

    @Test
    public void should_return_empty_array_when_no_books_lent() {
        this.etlEphelant.setFriend(null);
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Arrays.asList(this.etlEphelant, this.etSesAmis));

        final List<Book> books = this.bookService.getAllLentBooks(1L);
        assertThat(books, hasSize(0));
    }

    @Test
    public void should_return_lent_books() {
        this.lesAventures.setFriend(this.cocoLapin);
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Collections.singletonList(this.lesAventures));

        final List<Book> books = this.bookService.getAllLentBooks(1L);
        assertThat(books, hasSize(1));
        assertThat(books.get(0).getTitle(), equalTo("Les aventures de Winnie l'ourson"));
    }

    @Test
    public void should_return_true_when_updating_book_if_book_exists_in_db() {
        final Book newBook = Book.builder()
                .id(2L)
                .title("Winnie l'Ourson et l'Eléphant")
                .build();

        when(this.bookRepository.findBookById(2L)).thenReturn(Optional.of(this.etlEphelant));
        when(this.bookRepository.updateBook(2L, newBook)).thenReturn(true);

        assertThat(this.bookService.update(2L, newBook)).isEqualTo(true);
    }

    @Test
    public void should_return_false_when_updating_book_if_book_does_not_exist_in_db() {
        final Book newBook = Book.builder()
                .id(5L)
                .title("Rencontre au pays des rêves bleus")
                .build();

        when(this.bookRepository.findBookById(5L)).thenReturn(Optional.empty());

        assertThat(this.bookService.update(5L, newBook)).isEqualTo(false);
    }
}
