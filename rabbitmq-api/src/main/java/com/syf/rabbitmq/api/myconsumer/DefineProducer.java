package com.syf.rabbitmq.api.myconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DefineProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_define_exchange";
        String routingKey = "define.save";

        String msg = "test my define message";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey,null, msg.getBytes());
        }

    }
}
