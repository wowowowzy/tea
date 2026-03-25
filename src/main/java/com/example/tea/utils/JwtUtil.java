package com.example.tea.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    // 密钥（建议在application.yml中配置，至少32位，保证安全性）
    @Value("${jwt.secret}")
    private String secret;

    // token过期时间（如2小时，单位：毫秒）
    @Value("${jwt.expire}")
    private long expire;

    /**
     * 生成JWT令牌（登录成功后返回给前端）
     * @param userId 用户ID（自定义数据，可加用户名、角色等）
     * @return JWT令牌字符串
     */
    public String generateToken(Long userId) {
        // 生成加密密钥（必须是256位/32字节以上，否则报错）
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        // 构建token
        return Jwts.builder()
                // 自定义声明（存储用户ID，前端不解析，后端验证后使用）
                .claim("userId", userId)
                // 签发时间
                .issuedAt(new Date())
                // 过期时间
                .expiration(new Date(System.currentTimeMillis() + expire))
                // 签名算法+密钥
                .signWith(key)
                // 构建并序列化
                .compact();
    }

    public String generateTokenAdmin(Long userId) {
        // 生成加密密钥（必须是256位/32字节以上，否则报错）
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        // 构建token
        return Jwts.builder()
                // 自定义声明（存储用户ID，前端不解析，后端验证后使用）
                .claim("userId", userId)
                .claim("remark","admin")
                // 签发时间
                .issuedAt(new Date())
                // 过期时间
                .expiration(new Date(System.currentTimeMillis() + expire))
                // 签名算法+密钥
                .signWith(key)
                // 构建并序列化
                .compact();
    }

    /**
     * 验证token是否有效（未过期、签名正确）
     * @param token JWT令牌
     * @return true=有效，false=无效
     */
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            // 解析token（自动验证签名和过期时间，异常则无效）
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // 解析失败=token无效（过期、签名错误、篡改等）
            return false;
        }
    }

    /**
     * 校验：Token 合法 + 必须是管理员（remark=admin）
     */
    public boolean validateAdminToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 核心：必须同时校验 remark == admin
            String remark = claims.get("remark", String.class);
            return "admin".equals(remark);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析token，获取自定义的用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        // 解析token获取声明
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        // 获取自定义的userId
        return claims.get("userId", Long.class);
    }

    public String geRemarkFromTokenAdmin(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        // 解析token获取声明
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        // 获取自定义的userId
        return claims.get("remark", String.class);
    }
}
