package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.model.po.Category;
import com.mmall.model.vo.CategoryVO;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(CategoryVO categoryVO) {
        if (categoryVO.getParentId() == null || StringUtils.isBlank(categoryVO.getName())) {
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }

        Category category = new Category(categoryVO);
        category.setStatus(true);
        category.setCreateTime((int)(System.currentTimeMillis()/1000));

        int rowCount = categoryMapper.insertSelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(CategoryVO categoryVO) {
        if (categoryVO.getId() == null || StringUtils.isBlank(categoryVO.getName())) {
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }

        Category category = new Category(categoryVO);
        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("更新品类名成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名错误");
    }
}
