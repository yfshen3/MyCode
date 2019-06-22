package com.mmall.model.vo;

import lombok.Data;

import javax.servlet.http.HttpSession;

@Data
public class LoginVO {
    private String userName;

    private String password;

    private HttpSession session;
}
