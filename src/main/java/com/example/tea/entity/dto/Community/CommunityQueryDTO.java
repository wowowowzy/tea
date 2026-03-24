package com.example.tea.entity.dto.Community;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityQueryDTO {
    private Integer page;

    private Integer pageSize;

    private String name;

    private Long userId;
}
