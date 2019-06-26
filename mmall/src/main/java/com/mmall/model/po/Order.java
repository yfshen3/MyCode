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

    private Integer paymentTime;

    private Integer sendTime;

    private Integer endTime;

    private Integer closeTime;

    private Integer createTime;

    private Integer upDateTime;

}