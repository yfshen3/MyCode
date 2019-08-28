package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.model.po.Category;
import com.mmall.model.vo.CategoryVO;

import java.util.List;

public interface ICategoryService {

    ServerResponse addCategory(CategoryVO categoryVO);

    ServerResponse updateCategoryName(CategoryVO categoryVO);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 递归查询本节点的id和孩子节点的id
     * @param categoryId
     * @return
     */
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
