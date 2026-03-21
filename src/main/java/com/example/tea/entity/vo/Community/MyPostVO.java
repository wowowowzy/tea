package com.example.tea.entity.vo.Community;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyPostVO {
    private String title;
    /** 帖子内容 */
    private String content;
    /** 点赞数 */
    private Integer likeCount;
    /** 评论数 */
    private Integer commentCount;
    private Integer collectCount;
    //帖子id
    private Long id;
    private String image;
}
