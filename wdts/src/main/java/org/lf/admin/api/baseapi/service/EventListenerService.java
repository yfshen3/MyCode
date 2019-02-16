package org.lf.admin.api.baseapi.service;

import org.lf.admin.api.baseapi.listener.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EventListenerService {
    /**
     * 上下文对象
     */
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String msg) {
        // 通过上下文对象发布监听
        applicationContext.publishEvent(new MyEvent(this, msg));
        System.out.println(msg);
    }
}
