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
    private int rating;
    private String comment;
    private Reader reader;
    private Friend friend;
    private List<Wishlist> wishlists;
    private List<Collection> collections;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages &&
                owned == book.owned &&
                rating == book.rating &&
                Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(image, book.image) &&
                Objects.equals(language, book.language) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(datePublished, book.datePublished) &&
                Objects.equals(synopsis, book.synopsis) &&
                Objects.equals(comment, book.comment) &&
                Objects.equals(reader, book.reader) &&
                Objects.equals(friend, book.friend) &&
                Objects.equals(wishlists, book.wishlists) &&
                Objects.equals(collections, book.collections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authors, isbn, image, language, publisher, datePublished, pages, synopsis, owned, rating, comment, reader, friend, wishlists, collections);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", isbn='" + isbn + '\'' +
                ", image='" + image + '\'' +
                ", language='" + language + '\'' +
                ", publisher='" + publisher + '\'' +
                ", datePublished=" + datePublished +
                ", pages=" + pages +
                ", synopsis='" + synopsis + '\'' +
                ", owned=" + owned +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reader=" + reader +
                ", friend=" + friend +
                ", wishlists=" + wishlists +
                ", collections=" + collections +
                '}';
    }
}
