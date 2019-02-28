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
    private String edition;
    private int page;
    private String overview;
    private String synopsis;
    private List<String> subjects;
    private String reviews_api;
    private Reader reader;
    private Friend friend;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return page == book.page &&
                Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(image, book.image) &&
                Objects.equals(language, book.language) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(datePublished, book.datePublished) &&
                Objects.equals(edition, book.edition) &&
                Objects.equals(overview, book.overview) &&
                Objects.equals(synopsis, book.synopsis) &&
                Objects.equals(subjects, book.subjects) &&
                Objects.equals(reviews_api, book.reviews_api) &&
                Objects.equals(reader, book.reader) &&
                Objects.equals(friend, book.friend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authors, isbn, image, language, publisher, datePublished, edition, page, overview, synopsis, subjects, reviews_api, reader, friend);
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
                ", date_published=" + datePublished +
                ", edition='" + edition + '\'' +
                ", page=" + page +
                ", overview='" + overview + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", subjects=" + subjects +
                ", reviews_api='" + reviews_api + '\'' +
                ", reader=" + reader +
                ", friend=" + friend +
                '}';
    }
}
