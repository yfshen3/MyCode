package org.lf.admin.api.baseapi.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.admin.api.baseapi.service.EventListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListenerTest {

    @Autowired
    private EventListenerService service;

    @Test
    public void testEvent(){
        service.publish("测试监听");
    }
}
