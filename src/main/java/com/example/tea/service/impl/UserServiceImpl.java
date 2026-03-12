package com.example.tea.service.impl;

import com.example.tea.entity.dto.User.LoginInfo;
import com.example.tea.entity.dto.User.RegisterInfo;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.pojo.User.User;
import com.example.tea.entity.vo.LoginResult;
import com.example.tea.mapper.UserMapper;
import com.example.tea.service.UserService;
import com.example.tea.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public LoginResult login(LoginInfo loginInfo) {
        User user = userMapper.login(loginInfo.getUsername());

        if (user == null) {
            return LoginResult.builder()
                    .state(0)
                    .reason("用户未注册")
                    .build(); // 直接返回“未注册”提示
        }

        boolean matches = bCryptPasswordEncoder.matches(loginInfo.getPassword(), user.getPassword());
        if(matches){
            return LoginResult.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .token(jwtUtil.generateToken(user.getId()))
                    .state(1)
                    .avatar(user.getAvatar())
                    .build();
        }else return LoginResult.builder().state(0).reason("密码错误").build();
    }

    @Override
    public Result register(RegisterInfo registerInfo) {
        if(registerInfo.getUsername()==null||registerInfo.getUsername().isBlank()){
            return Result.error("用户名为空");
        }
        if(registerInfo.getPassword()==null||registerInfo.getPassword().isBlank()){
            return Result.error("密码为空");
        }
        if(registerInfo.getPhone()==null||registerInfo.getPhone().isBlank()){
            return Result.error("电话号码为空");
        }
        User user = User.builder()
                .username(registerInfo.getUsername())
                .password(bCryptPasswordEncoder.encode(registerInfo.getPassword()))
                .phone(registerInfo.getPhone())
                .createTime(LocalDateTime.now())
                .status(1)
                .avatar("https://cn.bing.com/images/search?view=detailV2&ccid=z8hLJ%2br4&id=82A56F87F6F5EF3AB8EA85F15B93A89A1B0662FA&thid=OIP.z8hLJ-r4jBsa1dYRotjnYgAAAA&mediaurl=https%3a%2f%2fc-ssl.dtstatic.com%2fuploads%2fitem%2f202003%2f18%2f20200318091411_bopif.thumb.400_0.jpg&exph=400&expw=400&q=%e5%a4%b4%e5%83%8f&mode=overlay&FORM=IQFRBA&ck=3BBBA5AB3E54B408DA5EFAF94BF6A469&selectedIndex=0&idpp=serp")
                .build();
        try {
            userMapper.insertUser(user);

        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (StringUtils.hasText(errorMsg)) {
                if (errorMsg.contains("idx_username")) {
                    return Result.error("用户名已存在，请更换用户名！");
                } else if (errorMsg.contains("idx_phone")) {
                    return Result.error("手机号已注册，请更换手机号！");
                }
            }
            return Result.error("用户名或手机号已存在，请更换后重试！");
        }
        return Result.success();
    }
}
