package com.mmall.model.po;

import lombok.Data;

@Data
public class Cart {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    private Long createTime;

    private Long updateTime;

}