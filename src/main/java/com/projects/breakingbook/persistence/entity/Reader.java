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
    private List<Friend> friends;

    public Reader(Long id) {
        this.id = id;
    }


}
