package com.news.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 收藏实体类
 */
@Data
@TableName("collect")
public class Collect implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 新闻id
    */
    private Integer newsId;
}