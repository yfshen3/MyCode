package com.mmall.model.po;

import lombok.Data;

@Data
public class PayInfo {
    private Integer id;

    private Integer userId;

    private Long orderNo;

    private Integer payPlatform;

    private String platformNumber;

    private String platformStatus;

    private Long createTime;

    private Long updateTime;

}