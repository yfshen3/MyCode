package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.model.po.User;
import com.mmall.model.vo.CheckValidVO;
import com.mmall.model.vo.UserVO;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServerResponse<String> register(UserVO userVO) {
        ServerResponse validResponse = this.checkValid(new CheckValidVO(userVO.getUsername(), Const.USERNAME));
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(new CheckValidVO(userVO.getEmail(), Const.EMAIL));
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        userVO.setRole(Const.Role.ROLE_CUSTOMER);
        User user = new User(userVO);
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(CheckValidVO checkValidVO) {
        if (StringUtils.isNotBlank(checkValidVO.getType())) {
            // 开始校验
            if (Const.USERNAME.equals(checkValidVO.getType())) {
                int resultCount = userMapper.checkUsername(checkValidVO.getStr());
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(checkValidVO.getType())) {
                int resultCount= userMapper.checkEmail(checkValidVO.getStr());
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(UserVO userVO) {
        ServerResponse validResponse = this.checkValid(new CheckValidVO(userVO.getUsername(), Const.USERNAME));
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(userVO.getUsername());
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccessData(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题为空");
    }

    @Override
    public ServerResponse<String> checkAnswer(UserVO userVO) {
        int resultCount = userMapper.checkAnswer(userVO.getUsername(), userVO.getQuestion(), userVO.getAnswer());
        if (resultCount > 0) {
            // 说明问题及问题答案是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey("token_" + userVO.getUsername(), forgetToken);
            return ServerResponse.createBySuccessData(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }
}
