package com.news.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * JWT 工具类
 */
@Component
public class JwtUtil {
    // 推荐使用 256 位（32字节）的安全密钥
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRE_TIME = 86400; // 24小时有效期（秒）

    /**
     * 生成安全的 JWT Token
     * @param username 用户名
     * @return 合法的 JWT 字符串
     * @throws WeakKeyException 密钥不满足安全要求时抛出
     */
    public String generateToken(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username); // 标准声明：主题
        claims.put("iss", "your-application"); // 标准声明：签发者（可选）
        claims.put("aud", "authorized-users"); // 标准声明：受众（可选）
        claims.put("iat", new Date(System.currentTimeMillis())); // 标准声明：签发时间

        return Jwts.builder()
                .setClaims(claims) // 设置自定义载荷
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000)) // 过期时间（毫秒）
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 使用安全密钥和算法
                .compact(); // 生成紧凑的 JWT 字符串
    }

    /**
     * 验证 JWT Token 的有效性
     * @param token JWT 字符串
     * @return 验证结果
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token); // 解析并验证签名
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 捕获令牌过期、签名错误、格式错误等异常
            return false;
        }
    }

    /**
     * 从 JWT Token 中获取用户名
     * @param token JWT 字符串
     * @return 用户名（解析失败时返回 null）
     */
    public static String getUsernameFromToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}