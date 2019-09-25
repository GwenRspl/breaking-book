package com.projects.breakingbook.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wishlist {
    private Long id;
    private String name;
    private User user;
    private List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id) &&
                Objects.equals(name, wishlist.name) &&
                Objects.equals(user, wishlist.user) &&
                Objects.equals(books, wishlist.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user, books);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", user=" + user +
                ", books=" + books +
                '}';
    }
}
