package com.example.tea.controller.admin;

import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.pojo.SysOperLog;
import com.example.tea.mapper.SysOperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * (Admin管理员)日志接口
 */
@RestController
@RequestMapping("/api/admin/log")
public class LogAdminController {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    /**
     * 日志展示
     * @return
     */
    @GetMapping("showLog")
    public Result showLog(){
         List<SysOperLog> logs = sysOperLogMapper.getAllLogList();
        return Result.success(logs);
    }
}
