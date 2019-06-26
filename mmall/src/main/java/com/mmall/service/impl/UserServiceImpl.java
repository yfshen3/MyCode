package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.model.po.User;
import com.mmall.model.vo.*;
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
    public ServerResponse<User> login(LoginVO loginVO) {
        int resultCount = userMapper.checkUsername(loginVO.getUsername());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(loginVO.getPassword());
        User user = userMapper.selectLogin(loginVO.getUsername(), md5Password);
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
        userVO.setCreateTime((int)(System.currentTimeMillis()/1000));
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
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + userVO.getUsername(), forgetToken);
            return ServerResponse.createBySuccessData(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(ForgetResetPasswordVO forgetResetPasswordVO) {
        if (StringUtils.isBlank(forgetResetPasswordVO.getForgetToken())) {
            return ServerResponse.createByErrorMessage("参数错误，token需要传递");
        }
        ServerResponse<String> validResponse = this.checkValid(new CheckValidVO(forgetResetPasswordVO.getUsername(),
                Const.USERNAME));
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + forgetResetPasswordVO.getUsername());
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }

        if (StringUtils.equals(token, forgetResetPasswordVO.getForgetToken())) {
            String md5Password = MD5Util.MD5EncodeUtf8(forgetResetPasswordVO.getNewPassword());
            int rowCount = userMapper.updatePasswordByUsername(forgetResetPasswordVO.getUsername(),
                    md5Password, (int)(System.currentTimeMillis()/1000));
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("token错误，请重新获取充值密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(ResetPasswordVO resetPasswordVO, User user) {
        /**
         * 防止横向越权，要校验一下这个用户的旧密码，一定指定是这个用户，因为我们会查询一个count(1),
         * 如果不指定id,那么结果就是true啦
         */
        String md5Password = MD5Util.MD5EncodeUtf8(resetPasswordVO.getOldPassword());
        int resultCount = userMapper.checkPassword(md5Password, user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(resetPasswordVO.getNewPassword()));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInformation(UserVO userVO) {
        /**
         * username是不能被更新的
         * email也要进行一个校验，校验email是不是已经存在，
         * 并且存在的email如果相同的话，不能是我们当前的这个用户的
         */
        int resultCount = userMapper.checkEmailByUserId(userVO.getEmail(), userVO.getId());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已存在，请更换email再尝试更新");
        }
        User updateUser = new User(userVO);
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess("更新成功个人信息成功", updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessData(user);
    }

    @Override
    public ServerResponse checkIsAdmin(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return  ServerResponse.createByError();
    }
}
