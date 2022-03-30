package com.cm.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
//@Component
public class AckReceiver implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        long deliveryTag = messageProperties.getDeliveryTag();
        String consumerQueue = messageProperties.getConsumerQueue();
        try {
            log.info("messageId:{},consumerQueue:{},deliveryTag:{}", messageId, consumerQueue, deliveryTag);
            // 根据队列名，写具体处理逻辑
            if ("queueName".equals(consumerQueue)) {
                log.info("queueName:{},message:{}", consumerQueue, message);
            }
            // 确认
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            // 拒绝
            channel.basicReject(deliveryTag, false);
            log.error("Exception:{}", e);
        }
    }

}
