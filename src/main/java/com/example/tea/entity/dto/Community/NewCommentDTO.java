package com.example.tea.entity.dto.Community;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewCommentDTO {
    /**
     * 关联的帖子ID
     */
    private Long postId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID：0-直接评论帖子，>0-回复某条评论
     */
    private Long parentId;

    /**
     * 回复的目标用户ID（仅回复时非0）
     */
    private Long toUserId;

}
