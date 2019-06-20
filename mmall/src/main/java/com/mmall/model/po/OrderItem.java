package com.mmall.model.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private Integer id;

    private Integer userId;

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Long createTime;

    private Long updateTime;

}