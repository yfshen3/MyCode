package com.syf.rabbitmq.adapter;

import com.syf.rabbitmq.entity.Order;
import com.syf.rabbitmq.entity.Packaged;

import java.util.Map;

public class MessageDelegate {

//    public void handleMessage(byte[] messageBody) {
//        System.err.println("默认方法，消息内容:" + new String(messageBody));
//    }

//    public void consumeMessage(byte[] messageBody) {
//        System.err.println("自定义方法，消息内容:" + new String(messageBody));
//    }

    public void consumeMessage(String messageBody) {
        System.err.println("字符串方法，消息内容:" + messageBody);
    }

    public void method1(String messageBody) {
        System.err.println("队列自定义方法，消息内容:" + messageBody);
    }

    public void consumeMessage(Map messageBody) {
        System.err.println("map方法，消息内容：" + messageBody);
    }

    public void consumeMessage(Order order) {
        System.err.println("order对象，消息内容，id: " + order.getId() +
                ", name: " + order.getName() + ", content: " + order.getContent());
    }

    public void consumeMessage(Packaged pack) {
        System.err.println("package对象，消息内容，id: " + pack.getId() +
                ", name: " + pack.getName() + ", content: " + pack.getDescription());
    }
}
