package com.cm.rabbitmq.consumer;

import com.cm.rabbitmq.config.MQConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
public class ConsumerService {

    @RabbitListener(queues = MQConfig.PRIORITY_QUEUE)
    public void process(Channel channel, Message message) throws IOException {
        String testMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("ConsumerService消费者收到消息:{}", testMessage);
        try {
            // 模拟执行任务
            //Thread.sleep(1000);
            // 模拟异常
            String is = null;
            is.toString();
            // 确认收到消息，false只确认当前consumer一个消息收到，true确认所有consumer获得的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // basicNack可以批量拒绝多条消息，而basicReject一次只能拒绝一条消息。
            if (message.getMessageProperties().getRedelivered()) {
                log.info("消息已重复处理失败,拒绝再次接收");
                // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } else {
                log.info("消息即将再次返回队列处理");
                // multiple 是否批量. true：将一次性拒绝所有小于deliveryTag的消息
                // equeue：被拒绝的是否重新入队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }

    }

    /*
    @RabbitListener(queues = MQConfig.TOPIC_QUEUE)
    public void processTopic(String testMessage) {
        String fanoutQueue = MQConfig.TOPIC_QUEUE;
        log.info("ConsumerService.processTopic 队列：{}, 消息:{}", fanoutQueue, testMessage);
    }
    */
/*

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE_1)
    public void processFanout(String testMessage) {
        String fanoutQueue = MQConfig.FANOUT_QUEUE_1;
        log.info("ConsumerService.processFanout 队列：{}, 消息:{}", fanoutQueue, testMessage);
    }
*/

}
