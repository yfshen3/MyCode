package com.mmall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForgetResetPasswordVO {

    private String username;

    private String newPassword;

    private String forgetToken;
}
