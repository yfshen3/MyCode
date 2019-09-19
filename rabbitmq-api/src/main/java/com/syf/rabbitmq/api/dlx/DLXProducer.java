package com.syf.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DLXProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";

        String msg = "test my dlx message";
        for (int i = 0; i < 5; i++) {
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("5000")
                    .build();

            channel.basicPublish(exchangeName, routingKey,properties, msg.getBytes());
        }

    }
}
