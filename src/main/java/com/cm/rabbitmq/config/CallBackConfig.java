package com.cm.rabbitmq.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Log4j2
@Configuration
public class CallBackConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        if (ack) {
            if (correlationData != null) {
                log.info("ID:{}", correlationData.getId());
            }
        } else {
            ReturnedMessage message = correlationData.getReturned();
            log.info("message:{}，错误原因：{}，交换机：{}，键：{}",
                    new String(message.getMessage().getBody()),
                    message.getReplyText(),
                    message.getExchange(),
                    message.getRoutingKey());
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("message:{}，错误原因：{}，交换机：{}，键：{}",
                new String(returnedMessage.getMessage().getBody()),
                returnedMessage.getReplyText(),
                returnedMessage.getExchange(),
                returnedMessage.getRoutingKey());
    }

}
