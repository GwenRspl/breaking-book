package com.projects.breakingbook.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Reader {
    private Long id;
    private String name;
    private String avatar;
    private String email;
    @JsonIgnore
    private String password;
    private List<Book> books;

    public Reader(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id) &&
                Objects.equals(name, reader.name) &&
                Objects.equals(avatar, reader.avatar) &&
                Objects.equals(email, reader.email) &&
                Objects.equals(password, reader.password) &&
                Objects.equals(books, reader.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, email, password, books);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", books=" + books +
                '}';
    }
}
