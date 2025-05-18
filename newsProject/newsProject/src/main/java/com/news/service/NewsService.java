package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.domain.News;
import com.news.vo.NewsVo;

import java.util.List;

/**
 * 新闻业务层接口
 */
public interface NewsService extends IService<News> {

    /**
     * 批量查询新闻
     * @return
     */
    List<NewsVo> getNewsByList(String auth);

    /**
     * 添加新闻浏览量
     * @param id
     */
    void addView(Long id);
}