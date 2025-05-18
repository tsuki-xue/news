package com.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.domain.User;
import com.news.mapper.UserMapper;
import com.news.service.UserService;
import com.news.util.JwtUtil;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User register(User user) {
        // 生成随机盐值
        String salt = new Sha256Hash(System.currentTimeMillis() + "").toHex();
        user.setSalt(salt);

        // 加密密码
        String encryptedPassword = encryptPassword(user.getPassword(), salt);
        user.setPassword(encryptedPassword);

        // 使用 MyBatis-Plus 的方法保存用户
        save(user);
        return user;
    }

    @Override
    public boolean validatePassword(String plainPassword, String encryptedPassword, String salt) {
        String newEncrypted = encryptPassword(plainPassword, salt);
        return newEncrypted.equals(encryptedPassword);
    }


    public String encryptPassword(String plainPassword, String salt) {
        return new Sha256Hash(plainPassword, salt, 1024).toHex();
    }


    @Override
    public String createJwtToken(String username) {
        return jwtUtil.generateToken(username);
    }
}