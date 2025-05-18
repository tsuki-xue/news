package com.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.news.domain.News;
import com.news.vo.NewsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 新闻mapper
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {

    /**查询管理员列表*/
    List<NewsVo> getNewsByAdminList();


    /**查询用户列表*/
    List<NewsVo> getNewsByUserList(String auth);


}