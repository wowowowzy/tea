package com.example.tea.controller.user;

import com.example.tea.entity.dto.User.LoginInfo;
import com.example.tea.entity.dto.User.RegisterInfo;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.LoginResult;
import com.example.tea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")//登录
    public Result login(LoginInfo loginInfo){
        LoginResult result = userService.login(loginInfo);
        if(result.getState()==1){
            return Result.success(result);
        }else return Result.error(result.getReason());
    }
    @PostMapping("/register")//注册
    public Result register(RegisterInfo registerInfo){
        return userService.register(registerInfo);
    }
}
