package org.lf.admin.api.baseapi.controller;

import org.lf.admin.api.baseapi.core.Result;
import org.lf.admin.api.baseapi.core.ResultGenerator;
import org.lf.admin.api.baseapi.rabbitmq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    MQSender sender;

    @RequestMapping("/mq")
    public Result mq() {
        sender.send("hello world");
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/topicmq")
    public Result topicmq() {
        sender.sendTopic("hello world");
        return ResultGenerator.genSuccessResult();
    }
}
