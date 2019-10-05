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

    private User polochon;
    private User dormeur;
    private Book aladdin;
    private Book blancheNeige;
    private Book laPetiteSirene;
    private Friend iago;

    @Before
    public void setUp() {
        this.polochon = User.builder()
                .id(1L)
                .username("Grincheux")
                .build();

        this.dormeur = User.builder()
                .id(2L)
                .username("Dormeur")
                .build();

        this.aladdin = Book.builder()
                .id(1L)
                .title("Aladdin et les quarante voleurs")
                .user(this.polochon)
                .friend(null)
                .build();

        this.blancheNeige = Book.builder()
                .id(2L)
                .title("Blanche-Neige et les sept nains")
                .comment("Histoire du soir")
                .user(this.dormeur)
                .friend(null)
                .build();

        this.laPetiteSirene = Book.builder()
                .id(3L)
                .title("La petite Sirène")
                .user(this.polochon)
                .friend(null)
                .build();

        this.iago = Friend.builder()
                .id(1L)
                .name("Tom")
                .build();
    }

    @Test
    public void should_list_All_Books() {
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Arrays.asList(this.aladdin, this.laPetiteSirene));

        final List<Book> books = this.bookService.getAll(1L);
        assertThat(books, hasSize(2));
        assertThat(books.get(0).getTitle(), equalTo("Aladdin et les quarante voleurs"));
        assertThat(books.get(1).getTitle(), equalTo("La petite Sirène"));
    }

    @Test
    public void should_return_empty_array_when_no_books_lent() {
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Arrays.asList(this.aladdin, this.laPetiteSirene));

        final List<Book> books = this.bookService.getAllLentBooks(1L);
        assertThat(books, hasSize(0));
    }

    @Test
    public void should_return_lent_books() {
        this.aladdin.setFriend(this.iago);
        when(this.bookRepository.findAllBooks(1L)).thenReturn(Collections.singletonList(this.aladdin));

        final List<Book> books = this.bookService.getAllLentBooks(1L);
        assertThat(books, hasSize(1));
        assertThat(books.get(0).getTitle(), equalTo("Aladdin et les quarante voleurs"));
    }

    @Test
    public void should_return_true_when_updating_book_if_book_exists_in_db() {
        final Book newBook = Book.builder()
                .id(2L)
                .title("Blanche-Neige")
                .build();

        when(this.bookRepository.findBookById(2L)).thenReturn(Optional.of(this.blancheNeige));
        when(this.bookRepository.updateBook(2L, newBook)).thenReturn(true);

        assertThat(this.bookService.update(2L, newBook)).isEqualTo(true);
    }

    @Test
    public void should_return_false_when_updating_book_if_book_does_not_exist_in_db() {
        final Book newBook = Book.builder()
                .id(5L)
                .title("La Belle et la Bête")
                .build();

        when(this.bookRepository.findBookById(5L)).thenReturn(Optional.empty());

        assertThat(this.bookService.update(5L, newBook)).isEqualTo(false);
    }
}
