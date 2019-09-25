package com.projects.breakingbook.exposition.DTO;

import com.projects.breakingbook.persistence.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String avatar;
    private List<Long> bookIds;
    private RoleName role;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", bookIds=" + bookIds +
                ", role=" + role +
                '}';
    }
}
