package com.example.tea.entity.dto.Community;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostWithUsernameDTO {
    /** 帖子ID */
    private Long id;
    /** 发布用户ID */
    private Long userId;
    /** 帖子标题 */
    private String title;
    /** 帖子内容 */
    private String content;
    /** 点赞数 */
    private Integer likeCount;
    /** 评论数 */
    private Integer commentCount;
    /** 收藏数 */
    private Integer collectCount;
    /** 状态：1-正常，0-删除，2-封禁 */
    private Integer status;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
    private String username;
}
