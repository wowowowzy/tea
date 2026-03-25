package com.example.tea.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysOperLog {
    private Long id;
    private String operName;        // 操作者
    private String operType;    // 操作类型
    private String operModule;  // 操作模块
    private String requestMethod; // 请求方法全路径
    private String requestParam;  // 请求参数
    private LocalDateTime operTime; // 操作时间
    private Integer status;     // 状态 1成功 0失败
    private String errorMsg;    // 错误信息
}
