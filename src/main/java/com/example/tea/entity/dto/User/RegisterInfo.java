package com.example.tea.entity.dto.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterInfo {
    private String username;
    private String password;
    private String phone;
}
