package com.example.tea.entity.pojo.Community;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Collect {
    private Long id;
    private Long userId;
    private Long postId;
    private Integer isCancel;
    private LocalDateTime createTime;
}
