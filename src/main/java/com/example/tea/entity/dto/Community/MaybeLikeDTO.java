package com.example.tea.entity.dto.Community;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaybeLikeDTO {
    private Long postId;
    private String title;
}
