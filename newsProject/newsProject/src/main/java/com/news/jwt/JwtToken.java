// com/news/jwt/JwtToken.java
package com.news.jwt;

import com.news.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT 令牌实现
 */
public class JwtToken implements AuthenticationToken {
    private final String token;
    private final String username;

    public JwtToken(String token) {
        this.token = token;
        // 从 JWT 中解析用户名（需使用 JwtUtil）
        this.username = JwtUtil.getUsernameFromToken(token);
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    // 以下方法为 AuthenticationToken 接口必填
    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return token; // 或返回 null，根据实际需求
    }
}