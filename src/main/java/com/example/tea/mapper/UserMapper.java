package com.example.tea.mapper;

import com.example.tea.entity.dto.User.UserQueryDTO;
import com.example.tea.entity.pojo.User.User;
import com.example.tea.entity.vo.User.UserVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from sys_user where username = #{username}")
    User login(String username);
    @Insert("INSERT INTO sys_user (username, password, phone, create_time, status,avatar) " +
            "VALUES (#{username}, #{password}, #{phone}, #{createTime}, #{status},#{avatar})")
    void insertUser(User user);



    @Update("update sys_user set avatar = #{avatar} where id = #{userId}")
    void setAvatar(String avatar,Long userId);

    @Select("select * from sys_user where id = #{userId} and status = 1")
    UserVO show(Long userId);

    @Select("select username from sys_user where id=#{userId}")
    String getNameByUserId(Long userId);

    Page<User> getUserList(UserQueryDTO dto);

    @Update("update sys_user set status = 0 where id = #{userId}")
    void banUser(Long userId);

    @Update("update sys_user set status = 1 where id = #{userId}")
    void switchBanUser(Long userId);
}
