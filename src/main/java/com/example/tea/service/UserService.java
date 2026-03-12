package com.example.tea.service;

import com.example.tea.entity.dto.User.LoginInfo;
import com.example.tea.entity.dto.User.RegisterInfo;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.LoginResult;

public interface UserService {
    LoginResult login(LoginInfo loginInfo);
    Result register(RegisterInfo registerInfo);
}
