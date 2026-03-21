package com.example.tea.entity.dto.Community;

import lombok.Builder;
import lombok.Data;

/**
 * 发布帖子请求DTO
 */
@Data
@Builder
public class PostCreateDTO {
    /** 帖子标题 */
    private String title;
    /** 帖子内容 */
    private String content;
}
