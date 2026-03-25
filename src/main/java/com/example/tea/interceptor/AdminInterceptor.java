package com.example.tea.interceptor;


import com.example.tea.utils.JwtUtil;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// 自定义拦截器，实现HandlerInterceptor接口
@Component // 注册为Spring Bean
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            // 返回401状态码
            response.setStatus(401);
            response.getWriter().write("未登录，请先登录");
            return false; // 返回false：拦截请求，不执行后续接口逻辑
        }
            if (jwtUtil.validateAdminToken(token)){
                ThreadLocalUserIdUtil.setCurrentId(jwtUtil.getUserIdFromToken(token));
            return true;
        }else {
            response.getWriter().write("不是管理员用户");
            response.setStatus(401);
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUserIdUtil.removeCurrentId();
    }

}
