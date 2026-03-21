package com.example.tea.entity.vo.Community;

import com.example.tea.entity.dto.Community.CommentDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子详情VO
 */
@Data
@Builder
public class PostDetailVO {
    /** 帖子ID */
    private Long postId;
    /** 发布用户ID */
    private Long userId;
    private String username;
    /** 帖子标题 */
    private String title;
    /** 帖子内容 */
    private String content;
    /** 点赞数 */
    private Integer likeCount;
    /** 评论数 */
    private Integer commentCount;
    /** 评论列表 */
    private List<CommentDTO> commentDTOS;
    /** 收藏数 */
    private Integer collectCount;
    /** 创建时间 */
    private LocalDateTime createTime;
    private String image;
}
