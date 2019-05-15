package com.projects.breakingbook.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Long id;
    private String title;
    private List<String> authors;
    private String isbn;
    private String image;
    private String language;
    private String publisher;
    private Date datePublished;
    private int pages;
    private String synopsis;
    private boolean owned;
    private BookStatus status;
    private int rating;
    private String comment;
    private Friend friend;
    private User user;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Book book = (Book) o;
        return this.pages == book.pages &&
                this.owned == book.owned &&
                this.rating == book.rating &&
                Objects.equals(this.id, book.id) &&
                Objects.equals(this.title, book.title) &&
                Objects.equals(this.authors, book.authors) &&
                Objects.equals(this.isbn, book.isbn) &&
                Objects.equals(this.image, book.image) &&
                Objects.equals(this.language, book.language) &&
                Objects.equals(this.publisher, book.publisher) &&
                Objects.equals(this.datePublished, book.datePublished) &&
                Objects.equals(this.synopsis, book.synopsis) &&
                this.status == book.status &&
                Objects.equals(this.comment, book.comment) &&
                Objects.equals(this.friend, book.friend) &&
                Objects.equals(this.user, book.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.authors, this.isbn, this.image, this.language, this.publisher, this.datePublished, this.pages, this.synopsis, this.owned, this.status, this.rating, this.comment, this.friend, this.user);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + this.id +
                ", title='" + this.title + '\'' +
                ", authors=" + this.authors +
                ", isbn='" + this.isbn + '\'' +
                ", image='" + this.image + '\'' +
                ", language='" + this.language + '\'' +
                ", publisher='" + this.publisher + '\'' +
                ", datePublished=" + this.datePublished +
                ", pages=" + this.pages +
                ", synopsis='" + this.synopsis + '\'' +
                ", owned=" + this.owned +
                ", status=" + this.status +
                ", rating=" + this.rating +
                ", comment='" + this.comment + '\'' +
                ", friendId=" + this.friend +
                ", user=" + this.user +
                '}';
    }
}
