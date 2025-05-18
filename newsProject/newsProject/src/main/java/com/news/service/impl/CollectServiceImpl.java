package com.news.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.domain.Collect;
import com.news.domain.News;
import com.news.mapper.CollectMapper;
import com.news.mapper.NewsMapper;
import com.news.service.CollectService;
import com.news.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private NewsMapper newsMapper;

    /**
     * 收藏新闻
     *
     * @param collect
     */
    @Override
    public void collect(Collect collect) {
        collectMapper.insert(collect); //新增收藏
        //新闻的收藏量 +1
        News news = newsMapper.selectById(collect.getNewsId());
        news.setCollectNum(news.getCollectNum()+1);
        newsMapper.updateById(news);
    }

    /**
     * 取消收藏
     * @param collect
     */
    @Override
    public void cancelCollect(Collect collect) {
        //根据条件删除收藏记录
        int rows = collectMapper.delete(
                new LambdaQueryWrapper<Collect>()
                        .eq(Collect::getUserName, collect.getUserName())
                        .eq(Collect::getNewsId, collect.getNewsId())
        ); // 删除收藏 使用mybatis-plus的delete方法删除收藏记录
        News news = newsMapper.selectById(collect.getNewsId()); //新闻的收藏量 -1
        news.setCollectNum(news.getCollectNum()-1);
        newsMapper.updateById(news);
    }

    /**
     * 查询收藏列表
     */
    @Override
    public List<NewsVo> getCollectList() {
        List<NewsVo> newsVos = new ArrayList<>();
        List<Collect> userCollectList = collectMapper.selectList(new LambdaQueryWrapper<Collect>()
                .eq(Collect::getUserName, "user"));
        for (Collect collect : userCollectList) {
            News news = newsMapper.selectById(collect.getNewsId());
            if (news != null){
                NewsVo newsVo = new NewsVo();
                BeanUtil.copyProperties(news, newsVo);
                newsVo.setIsCollect(1);
                newsVos.add(newsVo);
            }
        }
        return newsVos;
    }
}
