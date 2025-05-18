package com.news;

import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring boot 启动类
 */
@SpringBootApplication(exclude = {
        // 排除冲突的自动配置类，保留其他自动配置
        ShiroWebAutoConfiguration.class,
        ShiroAutoConfiguration.class
})
public class NewsProjectServerApp {

    public static void main(String[] args) {
        SpringApplication.run(NewsProjectServerApp.class, args);
    }
}
