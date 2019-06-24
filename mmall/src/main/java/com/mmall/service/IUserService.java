package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.model.po.User;
import com.mmall.model.vo.CheckValidVO;
import com.mmall.model.vo.UserVO;

/**
 * @author yfshen
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(UserVO userVO);

    ServerResponse<String> checkValid(CheckValidVO checkValidVO);

    ServerResponse<String> selectQuestion(UserVO userVO);

    ServerResponse<String> checkAnswer(UserVO userVO);
}
