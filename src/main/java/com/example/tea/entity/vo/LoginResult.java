package com.example.tea.entity.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResult {
    private Long id;
    private String username;
    private String token;
    private int state;//1表示成功 0表示失败
    private String reason;
    private String avatar;//头像
    private Long sessionId;//ai会话id
}
