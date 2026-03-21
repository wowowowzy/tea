package com.example.tea.entity.pojo.Community;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论/回复实体类（对应数据库 t_comment 表）
 */
@Data
@Builder
public class Comment {
    /**
     * 评论ID（主键）
     */
    private Long id;

    /**
     * 关联的帖子ID
     */
    private Long postId;

    /**
     * 评论用户ID
     */
    private Long userId;

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

    /**
     * 评论点赞数
     */
    private Integer likeCount;

    /**
     * 状态：1-正常，0-删除，2-封禁
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
