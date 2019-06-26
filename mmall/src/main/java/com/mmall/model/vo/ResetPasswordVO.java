package com.mmall.model.vo;

import lombok.Data;

@Data
public class ResetPasswordVO {

    private String oldPassword;

    private String newPassword;
}
