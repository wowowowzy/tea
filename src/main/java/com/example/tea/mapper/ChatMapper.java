package com.example.tea.mapper;

import com.example.tea.entity.pojo.ChatModel.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMapper {
    void add(History history);

    @Select("select * from sys_history where session_id=#{sessionId}")
    List<History> getHisories(long sessionId);
}
