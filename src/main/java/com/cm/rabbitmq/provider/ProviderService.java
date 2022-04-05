package com.cm.rabbitmq.provider;

import com.cm.rabbitmq.config.MQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class ProviderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接交换机、优先队列
     */
    public void sendPriorityMsg(String message, Integer priority) {
        // String exchange = MQConfig.PRIORITY_EXCHANGE;
        String exchange = MQConfig.PRIORITY_EXCHANGE + 1; // 确认回调测试
        String routingKey = MQConfig.PRIORITY_ROUTING_KEY;
        // String routingKey = MQConfig.PRIORITY_ROUTING_KEY +1; // returnedMessage 测试
        CorrelationData correlationDat = new CorrelationData();
        ReturnedMessage returned = new ReturnedMessage(new Message(message.getBytes(StandardCharsets.UTF_8)), 400, "错误", exchange, routingKey);
        correlationDat.setReturned(returned);
        correlationDat.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                message,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setPriority(priority);
                    return messagePostProcessor;
                },
                correlationDat
        );
    }

    public void sendTopicMsg() {
        // String routingKey = "topic.routing1.hello";
        String topicExchange = MQConfig.TOPIC_EXCHANGE + "99";

        String routingKey = "topic.routing.hello";
        // String topicExchange = MQConfig.TOPIC_EXCHANGE;
        rabbitTemplate.convertAndSend(topicExchange, routingKey, "你好！" + topicExchange);
    }

    public void sendFanoutMsg() {
        String fanoutExchange = MQConfig.FANOUT_EXCHANGE;
        rabbitTemplate.convertAndSend(fanoutExchange, null, "你好！" + MQConfig.FANOUT_EXCHANGE);
    }

}
