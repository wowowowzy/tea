package com.example.tea.entity.vo.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {
    private Long userId;
    private String username;
    private String token;
    private int state;//1表示成功 0表示失败
    private String reason;
    private String avatar;//头像
    private Long sessionId;//ai会话id
}
