package org.lf.admin.api.baseapi.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyAnnotationListener {

    @Async
    @EventListener
    public void listener1(MyEvent event) {
        System.out.println(Thread.currentThread().getName());
        System.out.println("注解事件监听-1:" + event.getMsg());
    }

    @Async
    @EventListener
    public void listener2(MyEvent event) {
        System.out.println(Thread.currentThread().getName());
        System.out.println("注解事件监听-2:" + event.getMsg());
    }
}
