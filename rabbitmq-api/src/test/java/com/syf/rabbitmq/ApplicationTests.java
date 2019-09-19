package com.syf.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syf.rabbitmq.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbitAdmin() {
        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
        rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));

        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));

        rabbitAdmin.declareBinding(new Binding("test.direct.queue",
                Binding.DestinationType.QUEUE,
                "test.direct",
                "direct",
                new HashMap<>()));

        rabbitAdmin.declareBinding(BindingBuilder
                .bind(new Queue("test.topic.queue", false))
                .to(new TopicExchange("test.topic", false, false))
                .with("topic.routingKey"));

        rabbitAdmin.declareBinding(BindingBuilder
                .bind(new Queue("test.fanout.queue", false))
                .to(new FanoutExchange("test.fanout", false, false)));
    }


    @Test
    public void testSendMessage() {
        MessageProperties properties = new MessageProperties();
        properties.setContentType("text/plain");
        properties.getHeaders().put("desc", "信息描述..");
        properties.getHeaders().put("type", "自定义消息类型..");
        Message message = new Message("Hello RabbitMQ".getBytes(), properties);

        rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.err.println("-----添加额外的设置-----");
                message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                message.getMessageProperties().getHeaders().put("attr", "额外添加额属性");
                return message;
            }
        });
    }

    @Test
    public void testSendJsonMessage() throws JsonProcessingException {
        Order order = new Order("001", "消息订单", "描述订单");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        System.err.println("order 4 json: " + json);

        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        Message message = new Message(json.getBytes(), properties);

        rabbitTemplate.convertAndSend("topic001", "spring.order", message);
    }

    @Test
    public void testSendJavaMessage() throws JsonProcessingException {
        Order order = new Order("001", "消息订单", "描述订单");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        System.err.println("order 4 json: " + json);

        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        properties.getHeaders().put("__TypeId__", "com.syf.rabbitmq.entity.Order");
        Message message = new Message(json.getBytes(), properties);

        rabbitTemplate.convertAndSend("topic001", "spring.order", message);
    }
}
