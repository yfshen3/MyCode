package com.syf.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectProducer {
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_direct_exchange";
        String routingKey = "test_direct_routingKey";

        String msg = "test direct exchange";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        channel.close();
        connection.close();
    }
}
