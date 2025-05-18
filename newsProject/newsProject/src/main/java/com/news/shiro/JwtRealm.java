package com.news.shiro;

import com.news.domain.User;
import com.news.jwt.JwtToken;
import com.news.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 自定义 Shiro Realm
 */
@Component
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    public JwtRealm() {
        setCredentialsMatcher(null); // 禁用凭证校验
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken; // 仅支持 JwtToken
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        JwtToken jwtToken = (JwtToken) token;
        String username = jwtToken.getUsername();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        // 第二个参数可任意，只要与 JwtToken.getCredentials() 返回值一致
        return new SimpleAuthenticationInfo(user, "jwt_credentials", getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 跳过匹配
    }
}
