package com.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.news.domain.Collect;
import com.news.vo.NewsVo;

import java.util.List;

/**
 * 收藏业务层接口
 */
public interface CollectService extends IService<Collect> {
    /**收藏新闻*/
    void collect(Collect collect);

    /**取消收藏*/
    void cancelCollect(Collect collect);

    /**查询收藏列表*/
    List<NewsVo> getCollectList();
}
