package com.news.controller;

import com.news.result.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

/**
 * shiro 测试
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info")
    @RequiresPermissions("user:view")
    public Result<?> getUserInfo() {
        return Result.success("这是用户信息，你有查看权限");
    }

    @GetMapping("/admin")
    @RequiresRoles("admin")
    public Result<?> adminInfo() {
        return Result.success("这是管理员信息，你有管理员权限");
    }

}    