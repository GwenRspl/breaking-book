package com.projects.breakingbook.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

}
