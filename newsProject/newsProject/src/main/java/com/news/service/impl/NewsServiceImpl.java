package com.news.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.config.redis.server.RedisService;
import com.news.domain.News;
import com.news.vo.NewsVo;
import com.news.mapper.NewsMapper;
import com.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private RedisService redisService;


    /**
     * 生成Redis缓存Key
     */
    private String generateRedisKey(String auth) {
        return "news:list:" + (auth != null ? auth : "default");
    }

    /**
     * 批量查询新闻
     * @return
     */
    @Override
    public List<NewsVo> getNewsByList(String auth) {
        String redisKey = generateRedisKey(auth); // 生成缓存Key
        //判断是什么用户进入
        if (auth.equals("admin")){
            List<NewsVo> newsByAdminList = newsMapper.getNewsByAdminList();
            redisService.setValue(redisKey, JSONUtil.toJsonStr(newsByAdminList));
            return newsByAdminList;//管理员的查询信息
        }
        List<NewsVo> newsByUserList = newsMapper.getNewsByUserList(auth);
        redisService.setValue(redisKey, JSONUtil.toJsonStr(newsByUserList));
        return newsByUserList;//普通用户的查询信息
    }

    /**
     * 添加新闻浏览量
     *
     * @param id
     */
    @Override
    public void addView(Long id) {
        News news = newsMapper.selectById(id);//查询新闻
        news.setLookNum(news.getLookNum() + 1);//观看量加1
        newsMapper.updateById(news);//更新
    }
}