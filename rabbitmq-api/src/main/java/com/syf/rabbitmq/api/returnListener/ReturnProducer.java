package com.syf.rabbitmq.api.returnListener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReturnProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "error.save";

        String msg = "test return message";
        channel.basicPublish(exchangeName, routingKeyError,true,null, msg.getBytes());

        // 添加一个return监听
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("replyCode: " + replyCode);
                System.err.println("replyText: " + replyText);
                System.err.println("exchange: " + exchange);
                System.err.println("routingKey: " + routingKey);
                System.err.println("properties: " + properties);
                System.err.println("msg: " + new String(body));
            }
        });
    }
}
