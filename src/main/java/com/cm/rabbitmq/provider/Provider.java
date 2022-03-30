package com.cm.rabbitmq.provider;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Provider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend("EXCHANGE",
                "ROUTING_KEY",
                "message",
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setPriority(10);
                    return messagePostProcessor;
                });
    }

}
