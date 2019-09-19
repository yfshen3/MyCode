package com.syf.rabbitmq.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DLXConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.*";
        String queueName = "test_dlx_queue";

        channel.exchangeDeclare(exchangeName, "topic", true);
        Map<String, Object> arguments = new HashMap<>();
        // 要进行死性队列的声明
        arguments.put("x-dead-letter-exchange", "dlx.exchange");

        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 要进行死性队列的声明
        channel.exchangeDeclare("dlx.exchange", "topic", true);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");

        channel.basicConsume("dlx.queue", true, new MyDLXConsumer(channel));
    }

}
