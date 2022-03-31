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
}
