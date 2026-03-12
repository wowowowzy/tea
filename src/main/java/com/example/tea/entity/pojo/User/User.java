package com.example.tea.entity.pojo.User;

import com.example.tea.entity.dto.User.RegisterInfo;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class User {
    // 对应表的id字段（自增，无需手动设置）
    private Long id;
    // 用户名
    private String username;
    // 加密后的密码
    private String password;
    // 手机号
    private String phone;
    // 头像
    private String avatar;
    // 状态（1-正常，0-禁用）
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    // 备注
    private String remark;
}
