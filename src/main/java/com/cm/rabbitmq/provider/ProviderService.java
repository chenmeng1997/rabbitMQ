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

    /**
     * 直接交换机、优先队列
     */
    public void sendMsg() {
        rabbitTemplate.convertAndSend(MQConfig.PRIORITY_EXCHANGE,
                MQConfig.PRIORITY_ROUTING_KEY,
                "message",
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setPriority(10);
                    return messagePostProcessor;
                });
    }

    public void sendTopicMsg() {
        // String routingKey = "topic.routing1.hello";
        // String topicExchange = MQConfig.TOPIC_EXCHANGE + "99";

        String routingKey = "topic.routing.hello";
        String topicExchange = MQConfig.TOPIC_EXCHANGE;
        rabbitTemplate.convertAndSend(topicExchange, routingKey, "你好！");
    }

}
