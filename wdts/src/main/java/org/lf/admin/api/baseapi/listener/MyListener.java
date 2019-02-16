package org.lf.admin.api.baseapi.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<MyEvent> {

    /**
     * 对监听到的事件进行处理
     * @param event
     */
    @Override
    public void onApplicationEvent(MyEvent event) {
        /**
         * 这里不做处理，只对消息进行透传打印，实际情况，
         * 可以根据项目进行逻辑处理。
         */
        event.printMsg(event.getMsg());
    }
}
