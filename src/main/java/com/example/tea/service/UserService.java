package com.example.tea.service;

import com.example.tea.entity.dto.User.LoginInfo;
import com.example.tea.entity.dto.User.RegisterInfo;
import com.example.tea.entity.dto.User.UserQueryDTO;
import com.example.tea.entity.pojo.PageResult;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.User.LoginVO;
import com.example.tea.entity.vo.User.UserVO;

public interface UserService {
    LoginVO login(LoginInfo loginInfo);
    Result register(RegisterInfo registerInfo);

    UserVO show() throws Exception;

    LoginVO loginAdmin(LoginInfo loginInfo);

    PageResult showUserAdmin(UserQueryDTO dto);

    Result banUser(Long userId);

    Result switchBanUser(Long userId);
}
