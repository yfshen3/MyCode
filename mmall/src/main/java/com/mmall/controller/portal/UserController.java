package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.model.po.User;
import com.mmall.model.vo.CheckValidVO;
import com.mmall.model.vo.LoginVO;
import com.mmall.model.vo.UserVO;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author yfshen
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<User> login(@RequestBody LoginVO loginVO, HttpSession session) {
        ServerResponse<User> response = userService.login(loginVO.getUserName(), loginVO.getPassword());
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ServerResponse<String> register(@RequestBody UserVO userVO) {
        return userService.register(userVO);
    }

    @RequestMapping(value = "/checkValid", method = RequestMethod.GET)
    public ServerResponse<String> checkValid(@RequestBody CheckValidVO checkValidVO) {
        return userService.checkValid(checkValidVO);
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccessData(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
    }

    @RequestMapping(value = "/forgetGetQuestion", method = RequestMethod.GET)
    public ServerResponse<String> forgetGetQuestion(@RequestBody UserVO userVO) {
        return userService.selectQuestion(userVO);
    }

    @RequestMapping(value = "/forgetCheckAnswer", method = RequestMethod.GET)
    public ServerResponse<String> forgetCheckAnswer(@RequestBody UserVO userVO) {
        return userService.checkAnswer(userVO);
    }

}
