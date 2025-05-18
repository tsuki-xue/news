package com.news.config.springmvc;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    //配置白名单
    private static final String[] whiteList = {"/login", "/signin","/getCode"};//登录，注册，获取验证码

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 放行请求
        for (String url : whiteList) {
            if (requestURI.contains(url)) {
                return true;
            }
        }

        // 获取 token
        String token = request.getHeader("Authorization");

        // 检查 token 是否存在
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"未授权,请重新登录\"}");
            return false;
        }
        // 验证 token 合法性解析token
        try {

        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"未授权,请重新登录\"}");
            return false;
        }
        return true;
    }
}