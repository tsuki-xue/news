package com.news.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.domain.Collect;
import com.news.domain.News;
import com.news.mapper.CollectMapper;
import com.news.vo.NewsVo;
import com.news.result.Result;
import com.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻管理
 */
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CollectMapper collectMapper;

    /**
     * 获取新闻列表
     * @return
     */
    @GetMapping("/list")
    public Result<List<NewsVo>> getNewsByList(@RequestParam String auth) {
        return Result.success(newsService.getNewsByList(auth));
    }

    /**
     * 新增
     * @param news
     * @return
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody News news) {
        newsService.save(news);
        return Result.success();
    }

    /**
     * 修改
     * @param news
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody News news) {
        newsService.updateById(news);
        return Result.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result<?> delete(@RequestParam Long id) {
        newsService.removeById(id);
        collectMapper.delete(new LambdaQueryWrapper<Collect>()
                .eq(Collect::getNewsId, id));//删除该新闻的收藏记录
        return Result.success();
    }

    /**
     * 点击查看增加浏览次数
     */
    @GetMapping("/addView")
    public Result<?> addView(@RequestParam Long id) {
        newsService.addView(id);
        return Result.success();
    }
}    