package org.lf.admin.api.baseapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("onlineCount")
public class OnlineCountController {

    @RequestMapping("count")
    @ResponseBody
    public String count(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 把sessionId记录在浏览器
            Cookie cookie = new Cookie("JSESSIONID", URLEncoder.encode(request.getSession().getId(), "utf-8"));
            cookie.setPath("/");
            //先设置cookie有效期为2天，不用担心，session不会保存2天
            cookie.setMaxAge( 48*60 * 60);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        Object count = session.getServletContext().getAttribute("count");
        return "count : " + count;
    }
}
