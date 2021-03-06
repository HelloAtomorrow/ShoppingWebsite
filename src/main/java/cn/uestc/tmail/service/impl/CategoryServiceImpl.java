package cn.uestc.tmail.service.impl;

import cn.uestc.tmail.mapper.CategoryMapper;
import cn.uestc.tmail.pojo.Category;
import cn.uestc.tmail.service.CategoryService;
import cn.uestc.tmail.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }

    @Override
    public Category get(Integer id) {
        return categoryMapper.get(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }
}
