package com.cm.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    // 确认
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String CONFIRM_ROUTING_KEY = "confirm.routing.key";

    // 备份
    public static final String BACKUP_EXCHANGE = "backup.exchange";
    public static final String BACKUP_QUEUE = "backup.queue";
    // 警告
    public static final String WARNING_QUEUE = "warning.queue";

    @Bean(value = "confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean(value = "confirmExchange")
    public DirectExchange confirmExchange() {
        // DirectExchange confirmExchange = new DirectExchange(CONFIRM_EXCHANGE);
        // 绑定备份交换机
        DirectExchange confirmExchange = ExchangeBuilder
                .directExchange(CONFIRM_EXCHANGE)
                .withArgument("alternate", BACKUP_EXCHANGE)
                .build();
        return confirmExchange;
    }

    /**
     * 确认机制
     *
     * @param queue    确认队列
     * @param exchange 确认交换机
     * @return 绑定
     */
    @Bean(value = "confirmQueueBindConfirmExchange")
    public Binding confirmQueueBindConfirmExchange(@Qualifier(value = "confirmQueue") Queue queue,
                                                   @Qualifier(value = "confirmExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean(value = "backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    @Bean(value = "warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean(value = "backupExchange")
    public FanoutExchange backupExchange() {
        FanoutExchange backupExchange = new FanoutExchange(BACKUP_EXCHANGE);
        return backupExchange;
    }

    @Bean(value = "backupQueueBindBackupExchange")
    public Binding backupQueueBindBackupExchange(@Qualifier(value = "backupQueue") Queue queue,
                                                 @Qualifier(value = "backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean(value = "warningQueueBindBackupExchange")
    public Binding warningQueueBindBackupExchange(@Qualifier(value = "warningQueue") Queue queue,
                                                  @Qualifier(value = "backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
