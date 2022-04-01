package com.cm.rabbitmq.consumer;

import com.cm.rabbitmq.config.MQConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ConsumerService {

    @RabbitListener(queues = MQConfig.PRIORITY_QUEUE)
    public void process(String testMessage) {
        log.info("ConsumerService消费者收到消息:{}", testMessage);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE)
    public void processTopic(String testMessage) {
        String fanoutQueue = MQConfig.TOPIC_QUEUE;
        log.info("ConsumerService.processTopic 队列：{}, 消息:{}", fanoutQueue, testMessage);
    }

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE_1)
    public void processFanout(String testMessage) {
        String fanoutQueue = MQConfig.FANOUT_QUEUE_1;
        log.info("ConsumerService.processFanout 队列：{}, 消息:{}", fanoutQueue, testMessage);
    }

}
