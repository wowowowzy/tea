package com.example.tea.entity.pojo.Community;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Like {
    private Long id;
    private Long userId;
    private Long targetId;
    private Integer targetType;
    private LocalDateTime createTime;
    private Integer isCancel;
    public static final Integer TYPE_POST = 1;
    public static final Integer TYPE_COMMENT = 2;
    public static final Integer VALID = 1;//有效
    public static final Integer INVALID = -1;//无效
}
