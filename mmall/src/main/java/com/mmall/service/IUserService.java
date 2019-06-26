package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.model.po.User;
import com.mmall.model.vo.*;

/**
 * @author yfshen
 */
public interface IUserService {
    ServerResponse<User> login(LoginVO loginVO);

    ServerResponse<String> register(UserVO userVO);

    ServerResponse<String> checkValid(CheckValidVO checkValidVO);

    ServerResponse<String> selectQuestion(UserVO userVO);

    ServerResponse<String> checkAnswer(UserVO userVO);

    ServerResponse<String> forgetResetPassword(ForgetResetPasswordVO forgetResetPasswordVO);

    ServerResponse<String> resetPassword(ResetPasswordVO resetPasswordVO, User user);

    ServerResponse<User> updateInformation(UserVO userVO);

    ServerResponse<User> getInformation(Integer userId);

    /**
     * backend
     */
    ServerResponse checkIsAdmin(User user);
}
