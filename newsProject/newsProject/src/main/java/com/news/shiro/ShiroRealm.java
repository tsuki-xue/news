package com.news.shiro;

import com.news.domain.User;
import com.news.jwt.JwtToken;
import com.news.service.UserService;
import com.news.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ShiroRealm extends AuthorizingRealm {

    public ShiroRealm() {
        // 配置密码匹配器（与注册时的加密方式完全一致）
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");      // 加密算法
        matcher.setHashIterations(1024);              // 迭代次数
        matcher.setStoredCredentialsHexEncoded(true); // 存储格式（HEX 或 Base64）
        setCredentialsMatcher(matcher);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // 重写 supports 方法，确保支持 UsernamePasswordToken
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof UsernamePasswordToken;
//    }

    // 重写 supports 方法，支持 JwtToken
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken || super.supports(token);
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 处理登录请求（用户名+密码）
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        User user = userService.findByUsername(username);
        // 验证密码...
        return new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
    }

    // 授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 根据用户角色添加权限
        if ("admin".equals(user.getUsername())) {
            info.addRole("admin");
            info.addStringPermission("*");
        } else {
            info.addRole("user");
            info.addStringPermission("user:view");
        }

        return info;
    }
}