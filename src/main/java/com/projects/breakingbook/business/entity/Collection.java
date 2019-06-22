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
public class Collection {
    private Long id;
    private String name;
    private User user;
    private List<Book> books;
}
