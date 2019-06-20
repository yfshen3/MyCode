package com.mmall.model.po;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Long createTime;

    private Long updateTime;

}