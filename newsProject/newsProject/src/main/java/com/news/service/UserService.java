package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.domain.User;

/**
 * 用户业务接口
 */
public interface UserService extends IService<User> {
    User findByUsername(String username);
    User register(User user);
    boolean validatePassword(String plainPassword, String encryptedPassword, String salt);

    // 新增 JWT 方法
    String createJwtToken(String username);
}