package com.cm.rabbitmq.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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

    /**
     * 确认消息发到交换机
     *
     * @param correlationData 相关数据
     * @param ack             确认
     * @param s               可选原因，用于 nack，如果可用，否则为 null。
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        Objects.requireNonNull(correlationData, "correlationData 为空！");
        if (ack) {
            log.info("ID:{}", correlationData.getId());
        } else {
            ReturnedMessage message = correlationData.getReturned();
            Objects.requireNonNull(message, "ReturnedMessage 为空！");
            log.info("message:{}，错误原因：{}，交换机：{}，键：{}",
                    new String(message.getMessage().getBody()),
                    message.getReplyText(),
                    message.getExchange(),
                    message.getRoutingKey());
        }
    }

    /**
     * 确认消息到队列
     *
     * @param returnedMessage 返回消息
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        Objects.requireNonNull(returnedMessage, "ReturnedMessage 为空！");
        log.error("message:{}，错误原因：{}，交换机：{}，键：{}",
                new String(returnedMessage.getMessage().getBody(), StandardCharsets.UTF_8),
                returnedMessage.getReplyText(),
                returnedMessage.getExchange(),
                returnedMessage.getRoutingKey());
    }

}
