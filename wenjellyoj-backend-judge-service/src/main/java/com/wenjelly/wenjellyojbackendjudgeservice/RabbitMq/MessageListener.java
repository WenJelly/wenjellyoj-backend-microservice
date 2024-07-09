package com.wenjelly.wenjellyojbackendjudgeservice.RabbitMq;

import com.rabbitmq.client.Channel;
import com.wenjelly.wenjellyojbackendserviceclient.service.JudgeFeignClient;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;


/**
 * 监听消息队列
 */
@Component
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    @Resource
    private JudgeFeignClient judgeFeignClient;

    // 定义交换机常量
    public static final String EXCHANGE_NAME = "exchange-code-box";

    // 定义队列常量
    public static final String QUEUE_NAME = "queue-code-box";

    // 定义路由常量
    public static final String ROUTING_KEY = "routing-key-code-box";

    // 通过注解来进行监听（监听+ 创建）
    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            // 监听的队列名称，durable是否持久化
            value = @Queue(value = QUEUE_NAME, durable = "true"),
            // 交换机名称
            exchange = @Exchange(value = EXCHANGE_NAME),
            // 路由规则
            key = {ROUTING_KEY}),
            ackMode = "MANUAL"
    )
    public void doMessage(String dataMessage, Message message, Channel channel) {

        log.info(" [x] Received '{}'", dataMessage);
        try {
            long questionSubmitId = Long.parseLong(dataMessage);
            judgeFeignClient.doJudge(questionSubmitId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }


    }
}
