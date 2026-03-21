package com.example.tea.entity.dto.Community;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDTO {
    private String title;
    /** 帖子内容 */
    private String content;
    //帖子id
    private Long postId;
}
