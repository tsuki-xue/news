package com.news.controller;

import com.news.config.redis.RedisKey;
import com.news.config.redis.server.RedisService;
import com.news.result.Result;
import com.news.service.UserService;
import com.news.dto.LoginDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录授权接口
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    RedisService redisService;

    /**
     * 登录接口 - 返回 登录所需要的token
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            String userName =(String)subject.getPrincipal();

            // 生成 JWT
            String jwtToken = userService.createJwtToken(userName);

            Map<String, Object> result = new HashMap<>();
            result.put("user", userName);
            result.put("token", jwtToken);
            result.put("message", "登录成功");
            //将个人信息存入redis
            redisService.setValue(RedisKey.USER+userName,jwtToken);
            return Result.success(result);
        } catch (AuthenticationException e) {
            return Result.error("认证失败: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        SecurityUtils.getSubject().logout();
        return Result.success("登出成功");
    }
}    