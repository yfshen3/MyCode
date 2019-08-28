package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.model.po.User;
import com.mmall.model.vo.CategoryVO;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public ServerResponse addCategory(@RequestBody CategoryVO categoryVO, HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验一下是否是管理员
        ServerResponse response = userService.checkIsAdmin(user);
        if (response.isSuccess()) {
            // 是管理员,增加处理分类的逻辑
            return categoryService.addCategory(categoryVO);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping(value = "/updateCategoryName", method = RequestMethod.POST)
    public ServerResponse updateCategoryName(@RequestBody CategoryVO categoryVO, HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验一下是否是管理员
        ServerResponse response = userService.checkIsAdmin(user);
        if (response.isSuccess()) {
            // 是管理员,增加处理分类的逻辑
            return categoryService.updateCategoryName(categoryVO);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping(value = "/getCategory", method = RequestMethod.POST)
    public ServerResponse getChildrenParallelCategory(@RequestBody CategoryVO categoryVO, HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验一下是否是管理员
        ServerResponse response = userService.checkIsAdmin(user);
        if (response.isSuccess()) {
            // 查询子节点的category id,不递归
            return categoryService.getChildrenParallelCategory(categoryVO.getId());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping(value = "/getDeepCategory", method = RequestMethod.POST)
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestBody CategoryVO categoryVO, HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验一下是否是管理员
        ServerResponse response = userService.checkIsAdmin(user);
        if (response.isSuccess()) {
            // 查询当前节点的id和递归子节点的id
            return categoryService.selectCategoryAndChildrenById(categoryVO.getId());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
}
