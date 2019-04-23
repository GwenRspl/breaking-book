package com.projects.breakingbook.exposition.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendDTO {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    private String avatar;
    private Long userId;
    private List<Long> historyBookIds;

    @Override
    public String toString() {
        return "FriendDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userId=" + userId +
                ", historyBookIds=" + historyBookIds +
                '}';
    }
}
