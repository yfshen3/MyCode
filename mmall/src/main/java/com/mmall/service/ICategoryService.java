package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.model.vo.CategoryVO;

public interface ICategoryService {

    ServerResponse addCategory(CategoryVO categoryVO);

    ServerResponse updateCategoryName(CategoryVO categoryVO);
}
