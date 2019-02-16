package org.lf.admin.api.baseapi.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MQReceive {

    private static final Logger logger = LoggerFactory.getLogger(MQReceive.class);

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String msg) {
        logger.info("receive msg:" + msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String msg) {
        logger.info("topic queue1 msg:" + msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String msg) {
        logger.info("topic queue2 msg:" + msg);
    }


}
