package com.projects.breakingbook.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String password;

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
                Objects.equals(password, reader.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, email, password);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
