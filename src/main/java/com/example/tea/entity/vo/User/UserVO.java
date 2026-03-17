package com.example.tea.entity.vo.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String userName;
    private String phone;
    private String avatar;
    private LocalDateTime createTime;
}
