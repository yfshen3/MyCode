package com.mmall.controller;

import com.mmall.dao.CartMapper;
import com.mmall.model.po.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private CartMapper cartMapper;

    @RequestMapping(value = "/getCart", method = RequestMethod.GET)
    public String testMethod() {
        Cart cart = cartMapper.selectByPrimaryKey(126);
        System.out.println(cart);
        return "hello world";
    }
}
