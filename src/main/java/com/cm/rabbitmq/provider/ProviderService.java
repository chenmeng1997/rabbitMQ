package com.cm.rabbitmq.provider;

import com.cm.rabbitmq.config.MQConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend(MQConfig.PRIORITY_EXCHANGE,
                MQConfig.PRIORITY_ROUTING_KEY,
                "message",
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setPriority(10);
                    return messagePostProcessor;
                });
    }

}
