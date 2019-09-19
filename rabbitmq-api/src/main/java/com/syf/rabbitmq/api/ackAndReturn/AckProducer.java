package com.syf.rabbitmq.api.ackAndReturn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class AckProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";

        for (int i = 0; i < 5; i++) {
            Map<String, Object> headers = new HashMap<>();
            headers.put("num", i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();

            String msg = "test ack message" + i;
            channel.basicPublish(exchangeName, routingKey,properties, msg.getBytes());
        }

    }
}
