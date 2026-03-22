package com.example.tea.entity.vo.Community;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyCollectVO {
    /** 帖子ID */
    private Long id;
    /**作者名字*/
    private String writerName;
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
    private String image;
}
