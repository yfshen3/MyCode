package com.mmall.model.po;

import com.mmall.model.vo.CategoryVO;
import lombok.Data;

@Data
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Integer createTime;

    private Integer updateTime;

    public Category(CategoryVO vo) {
        this.id = vo.getId();
        this.parentId = vo.getParentId();
        this.name = vo.getName();
        this.status = vo.getStatus();
        this.sortOrder = vo.getSortOrder();
        this.createTime = vo.getCreateTime();
        this.updateTime = vo.getUpdateTime();
    }

}