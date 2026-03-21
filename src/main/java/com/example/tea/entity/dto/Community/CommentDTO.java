package com.example.tea.entity.dto.Community;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    public Long id;
    private String username;
    private Long userId;
    private String content;
    private Long parentId;
    private Long toUserId;
    private Integer likeCount;
    private Long postId;


}
