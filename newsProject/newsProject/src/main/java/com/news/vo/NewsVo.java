package com.news.vo;

import com.news.domain.News;
import lombok.Data;

/**
 * 新闻传输对象
 */
@Data
public class NewsVo extends News {

    /**
     * 是否收藏
     */
    private Integer isCollect;
}
