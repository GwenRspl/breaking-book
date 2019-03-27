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
public class Friend {
    private Long id;
    private String name;
    private String avatar;
    private Reader reader;
    private List<Book> history;

    public Friend(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", reader=" + reader +
                ", history=" + history +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return Objects.equals(id, friend.id) &&
                Objects.equals(name, friend.name) &&
                Objects.equals(avatar, friend.avatar) &&
                Objects.equals(reader, friend.reader) &&
                Objects.equals(history, friend.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, reader, history);
    }
}
