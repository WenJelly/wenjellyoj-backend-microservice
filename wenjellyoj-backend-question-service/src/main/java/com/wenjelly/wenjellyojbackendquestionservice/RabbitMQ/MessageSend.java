package com.wenjelly.wenjellyojbackendquestionservice.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息队列发送端
 */
@Component
public class MessageSend {

    // 定义交换机常量
    public static final String EXCHANGE_NAME = "exchange-code-box";

    // 定义队列常量
    public static final String QUEUE_NAME = "queue-code-box";

    // 定义路由常量
    public static final String ROUTING_KEY = "routing-key-code-box";

    // 引入RabbitMQ
    @Resource
    private RabbitTemplate rabbitTemplate;


    public void doMessage(Long questionSubmitId) {
        // 将题目id转化成字符串
        String dateMessage = String.valueOf(questionSubmitId);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, dateMessage);
    }
}
