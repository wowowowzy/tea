package com.example.tea.mapper;

import com.example.tea.entity.pojo.SysOperLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysOperLogMapper {
    void insert(SysOperLog log);

    @Select("select * from sys_oper_log")
    List<SysOperLog> getAllLogList();

}
