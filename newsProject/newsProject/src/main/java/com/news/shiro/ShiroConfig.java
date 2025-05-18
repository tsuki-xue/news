package com.news.shiro;

import com.news.filter.JwtFilter;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.*;

import java.util.Arrays;


import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro 配置类
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, JwtFilter jwtFilter) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 自定义 JWT 过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwtFilter", jwtFilter);
        factoryBean.setFilters(filters);

        // 配置过滤规则
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("/api/auth/login", "anon");      // 登录接口放行
        filterMap.put("/api/auth/register", "anon");   // 注册接口放行
        filterMap.put("/static/**", "anon");           // 静态资源放行
        filterMap.put("/**", "anon[OPTIONS], jwtFilter");  // 其他接口需要 JWT 认证

        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/api/auth/login", "anon");
        chainDefinition.addPathDefinition("/api/auth/register", "anon");
        chainDefinition.addPathDefinition("/**", "jwt");
        return chainDefinition;
    }

    @Bean("customSecurityManager")
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);

        // 禁用 session（无状态 JWT 模式）
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(evaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Autowired
    private JwtRealm jwtRealm;

    @Autowired
    private ShiroRealm shiroRealm;

    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();

        // 设置多 Realm
        authenticator.setRealms(Arrays.asList(jwtRealm, shiroRealm));

        // 设置认证策略：第一个成功的 Realm 处理
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());

        return authenticator;
    }

    // 定义 SessionStorageEvaluator Bean（禁用 Session）
    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        return evaluator;
    }

    // 定义 SubjectDAO Bean
    @Bean
    public SubjectDAO subjectDAO(SessionStorageEvaluator evaluator) {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(evaluator);
        return subjectDAO;
    }

    // 定义 SecurityManager Bean
    @Bean
    public SecurityManager securityManager(ShiroRealm realm,
                                           Authenticator authenticator,
                                           SubjectDAO subjectDAO) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setAuthenticator(authenticator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }
}