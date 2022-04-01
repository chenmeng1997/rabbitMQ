package com.cm.rabbitmq;

import com.cm.rabbitmq.provider.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitMqDemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProviderService providerService;

    @Test
    void contextLoads() {
        providerService.sendMsg();
    }

    @Test
    void sendTopicMsg() {
        providerService.sendTopicMsg();
    }

}
