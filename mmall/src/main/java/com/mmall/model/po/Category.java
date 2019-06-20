package com.mmall.model.po;

import lombok.Data;

@Data
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Long createTime;

    private Long updateTime;

}