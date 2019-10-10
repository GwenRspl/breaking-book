package com.projects.breakingbook.business.service.utils;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.BookStatus;
import com.projects.breakingbook.business.entity.Friend;
import com.projects.breakingbook.business.entity.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceUtilsTest {
    private static final String DEFAULT_STRING = "default value";
    private static final String ORIGINAL_STRING = "original value";
    private static final String NULL_STRING = null;
    private static final int DEFAULT_INT = 42;
    private static final int ORIGINAL_INT = 2012;
    private static final int ZERO_INT = 0;

    final List<String> authors = Arrays.asList("J. R. R. Tolkien", "René Barjavel");
    final User user = User.builder()
            .id(5L)
            .build();
    final Friend friend = Friend.builder()
            .id(1L)
            .build();
    final Book originalBook = Book.builder()
            .id(2L)
            .title("La nuit des anneaux")
            .authors(this.authors)
            .isbn("123")
            .image("image")
            .language("FR")
            .publisher("Nazgul Editions")
            .datePublished(new Date(1568937600021L))
            .synopsis("Une aventure incroyable !")
            .owned(true)
            .rating(2)
            .user(this.user)
            .status(BookStatus.READ)
            .comment("C'était bof, le héros aurait mieux fait de rester chez lui.")
            .pages(234)
            .friend(this.friend)
            .build();
    final Book newBook = Book.builder()
            .id(2L)
            .title("La nuit des deux tours")
            .status(BookStatus.READ)
            .language("FR")
            .owned(true)
            .build();
    final Book expectedBook = Book.builder()
            .id(2L)
            .title("La nuit des deux tours")
            .authors(this.authors)
            .isbn("123")
            .image("image")
            .language("FR")
            .publisher("Nazgul Editions")
            .datePublished(new Date(1568937600021L))
            .synopsis("Une aventure incroyable !")
            .owned(true)
            .status(BookStatus.READ)
            .rating(2)
            .user(this.user)
            .comment("C'était bof, le héros aurait mieux fait de rester chez lui.")
            .pages(234)
            .friend(this.friend)
            .build();

    @Test
    public void should_return_default_value_when_value_is_null() {
        final String expectedString = DEFAULT_STRING;
        final String actualString = ServiceUtils.getValueOrDefaultString(NULL_STRING, DEFAULT_STRING);
        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    public void should_return_original_value_when_original_value_is_not_null() {
        final String expectedString = ORIGINAL_STRING;
        final String actualString = ServiceUtils.getValueOrDefaultString(ORIGINAL_STRING, DEFAULT_STRING);
        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    public void should_return_default_value_when_value_is_zero() {
        final int expectedInt = DEFAULT_INT;
        final int actualInt = ServiceUtils.getValueOrDefaultInteger(ZERO_INT, DEFAULT_INT);
        assertThat(actualInt).isEqualTo(expectedInt);
    }

    @Test
    public void should_return_original_value_when_value_is_not_zero() {
        final int expectedInt = ORIGINAL_INT;
        final int actualInt = ServiceUtils.getValueOrDefaultInteger(ORIGINAL_INT, DEFAULT_INT);
        assertThat(actualInt).isEqualTo(expectedInt);
    }

    @Test
    public void should_generate_books_attributes() {
        final Book actualBook = ServiceUtils.generateBooksAttributes(this.newBook, this.originalBook);
        final SoftAssertions should = new SoftAssertions();
        should.assertThat(actualBook.getTitle()).as("Title").isEqualTo(this.expectedBook.getTitle());
        should.assertThat(actualBook.getAuthors()).as("authors").isEqualTo(this.expectedBook.getAuthors());
        should.assertThat(actualBook.getIsbn()).as("isbn").isEqualTo(this.expectedBook.getIsbn());
        should.assertThat(actualBook.getImage()).as("image").isEqualTo(this.expectedBook.getImage());
        should.assertThat(actualBook.getLanguage()).as("language").isEqualTo(this.expectedBook.getLanguage());
        should.assertThat(actualBook.getPublisher()).as("publisher").isEqualTo(this.expectedBook.getPublisher());
        should.assertThat(actualBook.getDatePublished()).as("date").isEqualTo(this.expectedBook.getDatePublished());
        should.assertThat(actualBook.getSynopsis()).as("synopsis").isEqualTo(this.expectedBook.getSynopsis());
        should.assertThat(actualBook.getRating()).as("rating").isEqualTo(this.expectedBook.getRating());
        should.assertThat(actualBook.getUser()).as("user").isEqualTo(this.expectedBook.getUser());
        should.assertThat(actualBook.getStatus()).as("status").isEqualTo(this.expectedBook.getStatus());
        should.assertThat(actualBook.isOwned()).as("owned").isEqualTo(this.expectedBook.isOwned());
        should.assertThat(actualBook.getComment()).as("comment").isEqualTo(this.expectedBook.getComment());
        should.assertThat(actualBook.getPages()).as("pages").isEqualTo(this.expectedBook.getPages());
        should.assertThat(actualBook.getFriend()).as("friend").isEqualTo(this.expectedBook.getFriend());
        should.assertAll();
        assertThat(actualBook).isEqualTo(this.expectedBook);
    }
}
