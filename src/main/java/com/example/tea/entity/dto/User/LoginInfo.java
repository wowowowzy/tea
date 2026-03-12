package com.example.tea.entity.dto.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginInfo {
    // 用户名
    private String username;
    // 加密后的密码
    private String password;
}
