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

    @Test
    public void shouldReturnDefaultValueWhenValueIsNull() {
        final String expectedString = "default value";
        final String actualString = ServiceUtils.getValueOrDefaultString(null, "default value");
        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    public void shouldReturnOriginalValueWhenValueIsNotNull() {
        final String expectedString = "value not null";
        final String actualString = ServiceUtils.getValueOrDefaultString("value not null", "default value");
        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    public void shouldReturnDefaultValueWhenValueIsZero() {
        final int expectedInt = 42;
        final int actualInt = ServiceUtils.getValueOrDefaultInteger(0, 42);
        assertThat(actualInt).isEqualTo(expectedInt);
    }

    @Test
    public void shouldReturnOriginalValueWhenValueIsNotZero() {
        final int expectedInt = 12;
        final int actualInt = ServiceUtils.getValueOrDefaultInteger(12, 42);
        assertThat(actualInt).isEqualTo(expectedInt);
    }

    @Test
    public void generateBooksAttributes() {
        final List<String> authors = Arrays.asList("Author 1", "Author 2");
        final User user = new User();
        user.setId(5L);
        final Friend friend = new Friend();
        friend.setId(1L);
        final Book book = Book.builder()
                .id(2L)
                .title("Maleficent")
                .authors(authors)
                .isbn("123")
                .image("image")
                .language("FR")
                .publisher("Delcourt")
                .datePublished(new Date(1568937600021L))
                .synopsis("blabla")
                .owned(true)
                .rating(2)
                .user(user)
                .status(BookStatus.READ)
                .comment("Fancy comment")
                .pages(234)
                .friend(friend)
                .build();

        final Book newBook = Book.builder()
                .id(2L)
                .title("Maleficent 2")
                .build();

        final Book expectedBook = Book.builder()
                .id(2L)
                .title("Maleficent 2")
                .authors(authors)
                .isbn("123")
                .image("image")
                .language("FR")
                .publisher("Delcourt")
                .datePublished(new Date(1568937600021L))
                .synopsis("blabla")
                .owned(true)
                .status(BookStatus.READ)
                .rating(2)
                .user(user)
                .comment("Fancy comment")
                .pages(234)
                .friend(friend)
                .build();

        final Book actualBook = ServiceUtils.generateBooksAttributes(newBook, book);

        final SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualBook.getTitle()).as("Title").isEqualTo(expectedBook.getTitle());
        softly.assertThat(actualBook.getAuthors()).as("authors").isEqualTo(expectedBook.getAuthors());
        softly.assertThat(actualBook.getIsbn()).as("isbn").isEqualTo(expectedBook.getIsbn());
        softly.assertThat(actualBook.getImage()).as("image").isEqualTo(expectedBook.getImage());
        softly.assertThat(actualBook.getLanguage()).as("language").isEqualTo(expectedBook.getLanguage());
        softly.assertThat(actualBook.getPublisher()).as("publisher").isEqualTo(expectedBook.getPublisher());
        softly.assertThat(actualBook.getDatePublished()).as("date").isEqualTo(expectedBook.getDatePublished());
        softly.assertThat(actualBook.getSynopsis()).as("synopsis").isEqualTo(expectedBook.getSynopsis());
        softly.assertThat(actualBook.getRating()).as("rating").isEqualTo(expectedBook.getRating());
        softly.assertThat(actualBook.getUser()).as("user").isEqualTo(expectedBook.getUser());
        softly.assertThat(actualBook.getComment()).as("comment").isEqualTo(expectedBook.getComment());
        softly.assertThat(actualBook.getPages()).as("pages").isEqualTo(expectedBook.getPages());
        softly.assertThat(actualBook.getFriend()).as("friend").isEqualTo(expectedBook.getFriend());
        softly.assertAll();

    }
}
