package com.projects.breakingbook.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String avatar;
    private String email;
    private String password;
    private List<Book> books;
    private RoleName role;

    public User(final Long id) {
        this.id = id;
    }

    public User(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
