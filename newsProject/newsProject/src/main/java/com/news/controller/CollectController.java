package com.news.controller;


import com.news.domain.Collect;
import com.news.result.Result;
import com.news.service.CollectService;
import com.news.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏管理
 */
@RestController
@RequestMapping("/api/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 收藏新闻
     */
    @PostMapping
    public Result<?> collect(@RequestBody Collect collect) {
        collectService.collect(collect);
        return Result.success();
    }

    /**
     * 取消收藏
     */
    @PostMapping("/cancel")
    public Result<?> cancelCollect(@RequestBody Collect collect) {
        collectService.cancelCollect(collect);
        return Result.success();
    }

    /**
     * 查询收藏列表
     */
    @GetMapping("/list")
    public Result<List<NewsVo>> getCollectList() {
        return Result.success(collectService.getCollectList());
    }
}
