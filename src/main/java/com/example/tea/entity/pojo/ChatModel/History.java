package com.example.tea.entity.pojo.ChatModel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class History {
    private Integer id;
    private LocalDateTime datetime;
    private String content;
    private String role;
    private Long sessionId;
}
