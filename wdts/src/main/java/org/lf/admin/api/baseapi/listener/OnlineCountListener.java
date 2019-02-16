package org.lf.admin.api.baseapi.listener;

import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
@Configuration
public class OnlineCountListener implements HttpSessionListener {
    /**
     * 记录session的数量
     */
    public int count = 0;

    /**
     * 监听session的创建，synchronized 防并发bug
     * @param se
     */
    @Override
    public synchronized void sessionCreated(HttpSessionEvent se) {
        System.out.println("【HttpSessionListener监听器】count++  增加");
        count ++;
        se.getSession().getServletContext().setAttribute("count", count);
    }

    /**
     * 监听session的销毁
     * @param se
     */
    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("【HttpSessionListener监听器】count--  减少");
        count --;
        se.getSession().getServletContext().setAttribute("count", count);
    }
}
