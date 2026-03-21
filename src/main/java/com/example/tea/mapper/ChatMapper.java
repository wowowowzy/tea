package com.example.tea.mapper;

import com.example.tea.entity.pojo.ChatModel.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMapper {
    void add(History history);

    @Select("select * from sys_history where session_id=#{sessionId} ORDER BY datetime DESC LIMIT 16")
    List<History> getHisories(long sessionId);

    @Select("select * from sys_history where user_id=#{userId}")
    List<History> getHisoriesByUserId(Long userId);
}
