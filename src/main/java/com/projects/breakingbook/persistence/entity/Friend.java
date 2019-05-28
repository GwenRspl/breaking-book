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
    private User user;
    private List<Book> history;

    public Friend(final Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + this.id +
                ", username='" + this.name + '\'' +
                ", avatar='" + this.avatar + '\'' +
                ", user=" + this.user +
                ", historyBookIds=" + this.history +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Friend friend = (Friend) o;
        return Objects.equals(this.id, friend.id) &&
                Objects.equals(this.name, friend.name) &&
                Objects.equals(this.avatar, friend.avatar) &&
                Objects.equals(this.user, friend.user) &&
                Objects.equals(this.history, friend.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.avatar, this.user, this.history);
    }
}
