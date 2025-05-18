package com.news.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
//@TableName("user") // 对应MySQL数据库表名
@TableName("USERS") // 对应达梦数据库表名
public class User {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private Boolean locked;
    private Date createTime;
    private Date updateTime;
}