package com.syf.rabbitmq.api.myconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DefineConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_define_exchange";
        String routingKey = "define.*";
        String queueName = "test_define_queue";

        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        channel.basicConsume(queueName, true, new MyConsumer(channel));
    }

}
