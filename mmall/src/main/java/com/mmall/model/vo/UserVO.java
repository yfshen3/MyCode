package com.mmall.model.vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Integer createTime;

    private Integer updateTime;
}
