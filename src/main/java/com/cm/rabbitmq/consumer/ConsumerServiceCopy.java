package com.cm.rabbitmq.consumer;

import com.cm.rabbitmq.config.MQConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ConsumerServiceCopy {

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE_2)
    public void processFanout(String testMessage) {
        String fanoutQueue = MQConfig.FANOUT_QUEUE_2;
        log.info("ConsumerServiceCopy.processFanout 队列：{}, 消息:{}", fanoutQueue, testMessage);
    }

}
