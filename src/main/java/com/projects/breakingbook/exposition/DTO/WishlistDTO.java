package com.projects.breakingbook.exposition.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistDTO {
    private Long id;
    private String name;
    private Long userId;
    private List<Long> booksIds;
}
