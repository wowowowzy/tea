package com.example.tea.mapper;

import com.example.tea.entity.pojo.User.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from sys_user where username = #{username}")
    User login(String username);
    @Insert("INSERT INTO sys_user (username, password, phone, create_time, status,avatar) " +
            "VALUES (#{username}, #{password}, #{phone}, #{createTime}, #{status},#{avatar})")
    void insertUser(User user);

    @Select("select remark from sys_user where id = #{userId}")
    String checkAdmin(Long userId);

    @Update("update sys_user set avatar = #{avatar} where id = #{userId}")
    void setAvatar(String avatar,Long userId);
}
