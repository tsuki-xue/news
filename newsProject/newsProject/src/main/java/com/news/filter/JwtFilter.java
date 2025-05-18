package com.news.filter;

import com.news.jwt.JwtToken;
import com.news.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 */
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response){
        String token = getJwtToken(request);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return new JwtToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String requestURI = httpRequest.getRequestURI();
        // **关键优化**：如果路径已被 Shiro 配置为放行（ anon），直接返回 true
        if (isLoginPath(requestURI) || isStaticPath(requestURI)||httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        // 其他路径进入认证流程
        return super.isAccessAllowed(request, response, mappedValue);
    }

    private boolean isLoginPath(String uri) {
        return uri.startsWith("/api/auth/login"); // 匹配登录接口路径
    }

    private boolean isStaticPath(String uri) {
        return uri.startsWith("/static/"); // 匹配静态资源路径
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getJwtToken(request);
        if (StringUtils.isEmpty(token)) {
            // 无 Token，返回未认证
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        if (!jwtUtil.validateToken(token)) {
            // Token 无效或过期
//            throw new ExpiredCredentialsException("Token 已过期或无效");
        }
        
        // 执行登录
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            // 返回错误信息
            httpResponse.getWriter().print("{\"code\":401,\"message\":\"" + e.getMessage() + "\"}");
        } catch (IOException ex) {
            // 忽略
        }
        return false;
    }

    private String getJwtToken(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader("Authorization");
    }
}