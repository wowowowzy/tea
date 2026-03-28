package com.example.tea.controller.admin;

import com.example.tea.entity.dto.User.LoginInfo;
import com.example.tea.entity.dto.User.UserQueryDTO;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.User.LoginVO;
import com.example.tea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (Admin管理员)用户管理接口
 */
@RestController
@RequestMapping("/api/admin/user")
public class UserAdminController {
    @Autowired
    private UserService userService;
    /**
     * 管理员登录
     * @param loginInfo 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginInfo loginInfo){
        LoginVO result = userService.loginAdmin(loginInfo);
        if(result.getState()==1){
            return Result.success(result);
        }else return Result.error(result.getReason());
    }
    /**
     * 管理员获取用户信息
     * @return 用户信息
     */
    @PostMapping("/showUserAdmin")
    public Result showUserAdmin(UserQueryDTO dto){
        try {
            return Result.success(userService.showUserAdmin(dto));
        }catch (Exception e){
            return Result.error("展示出错");
        }
    }

    /**
     * 封禁用户
     * @param userId
     * @return
     */
    @PostMapping("/banUser")
    public Result banUser(@RequestParam(value = "userId") Long userId){
        return userService.banUser(userId);
    }
    /**
     * 解禁用户
     * @param userId
     * @return
     */
    @GetMapping ("/switchBanUser")
    public Result switchBanUser(@RequestParam(value = "userId") Long userId){
        return userService.switchBanUser(userId);
    }
}
