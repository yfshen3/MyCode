package com.mmall.model.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Integer id;

    private Long orderNo;

    private Integer userId;

    private Integer shippingId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Long paymentTime;

    private Long sendTime;

    private Long endTime;

    private Long closeTime;

    private Long createTime;

    private Long upDateTime;

}