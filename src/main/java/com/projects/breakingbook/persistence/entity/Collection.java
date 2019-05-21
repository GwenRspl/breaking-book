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
public class Collection {
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
        final Collection that = (Collection) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.user, that.user) &&
                Objects.equals(this.books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.user, this.books);
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", user=" + this.user +
                ", books=" + this.books +
                '}';
    }
}
