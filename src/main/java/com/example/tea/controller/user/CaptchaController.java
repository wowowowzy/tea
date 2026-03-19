package com.example.tea.controller.user;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.example.tea.entity.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


/**
 * Hutool验证码接口（极简版）
 */
@RestController
@RequestMapping("api/user/captcha")
@Slf4j
public class CaptchaController {

    /**
     * 生成验证码图片（线干扰型）
     */
    @GetMapping("/image")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 创建线干扰验证码：宽120，高40，4位字符，5条干扰线CaptchaUtil
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 5);

        // 2. 获取验证码字符（用于后续验证）
        String captchaCode = captcha.getCode();

        // 3. 存入Session
        HttpSession session = request.getSession();
        session.setAttribute("CaptchaCode", captchaCode.toLowerCase());

        // 4. 直接输出到浏览器（Hutool封装了ImageIO，一行搞定）
        captcha.write(response.getOutputStream());
    }

    /**
     * 验证验证码
     */
    @PostMapping("/verify")
    public Result verifyCaptcha(@RequestParam("code") String userInputCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String storedCode = (String) session.getAttribute("CaptchaCode");
        if (storedCode == null) {
            return Result.error("验证码已过期");
        }

        boolean isMatch = storedCode.equalsIgnoreCase(userInputCode);
        if (isMatch) {
            session.removeAttribute("CaptchaCode"); // 验证成功后删除
            return Result.success();
        } else {
            return Result.error("验证码错误");
        }
    }
}
