package com.mmall.model.vo;

import lombok.Data;

@Data
public class CategoryVO {
    private Integer id;

    private Integer parentId = 0;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Integer createTime;

    private Integer updateTime;

}
