package com.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.news.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类数据访问层
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}    