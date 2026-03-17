package com.example.tea.controller.user;

import com.example.tea.entity.dto.User.LoginInfo;
import com.example.tea.entity.dto.User.RegisterInfo;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.User.LoginVO;
import com.example.tea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")//登录
    public Result login(@RequestBody LoginInfo loginInfo){
        LoginVO result = userService.login(loginInfo);
        if(result.getState()==1){
            return Result.success(result);
        }else return Result.error(result.getReason());
    }
    @PostMapping("/register")//注册
    public Result register(@RequestBody RegisterInfo registerInfo){
        return userService.register(registerInfo);
    }

    /**
     * 展示主页
     * @return
     */
    @GetMapping("/show")
    public Result show(){
        try {
            return Result.success(userService.show());
        }catch (Exception e){
            return Result.error("展示出错");
        }
    }
}
