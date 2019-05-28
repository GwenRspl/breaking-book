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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Wishlist wishlist = (Wishlist) o;
        return Objects.equals(this.id, wishlist.id) &&
                Objects.equals(this.name, wishlist.name) &&
                Objects.equals(this.user, wishlist.user) &&
                Objects.equals(this.books, wishlist.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.user, this.books);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + this.id +
                ", username='" + this.name + '\'' +
                ", user=" + this.user +
                ", books=" + this.books +
                '}';
    }
}
