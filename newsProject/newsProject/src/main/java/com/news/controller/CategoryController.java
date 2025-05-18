package com.news.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.domain.Category;
import com.news.domain.News;
import com.news.mapper.NewsMapper;
import com.news.result.Result;
import com.news.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制类
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NewsMapper newsMapper;

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list() {
        return Result.success(categoryService.list());
    }

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result<?> delete(@RequestParam Integer id) {
        //查询当前分类下是否有新闻
        List<News> news = newsMapper.selectList(new LambdaQueryWrapper<News>()
                .eq(News::getCategoryId, id));
        if (news.size() > 0) {
            return Result.error("当前分类下有新闻，请先删除新闻");
        }
        categoryService.removeById(id);
        return Result.success();
    }
}    