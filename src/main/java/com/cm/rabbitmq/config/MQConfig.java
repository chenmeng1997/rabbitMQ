package com.cm.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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

    // 优先级队列
    public static final String PRIORITY_QUEUE = "priority.queue";
    public static final String PRIORITY_EXCHANGE = "priority.exchange";
    public static final String PRIORITY_ROUTING_KEY = "priority.routing.key";

    // 主题
    public static final String TOPIC_QUEUE = "topic.queue";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String TOPIC_ROUTING_KEY = "topic.routing.#";

    // 发布订阅
    public static final String FANOUT_QUEUE_1 = "fanout.queue.one";
    public static final String FANOUT_QUEUE_2 = "fanout.queue.two";
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    @Bean(value = "fanoutQueueOne")
    public Queue fanoutQueueOne() {
        return QueueBuilder.durable(FANOUT_QUEUE_1).build();
    }

    @Bean(value = "fanoutQueueTwo")
    public Queue fanoutQueueTwo() {
        return QueueBuilder.durable(FANOUT_QUEUE_2).build();
    }

    @Bean(value = "fanoutExchange")
    public FanoutExchange fanoutExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE);
        return fanoutExchange;
    }

    @Bean(value = "fanoutQueueOneQueueBindFanoutExchange")
    public Binding fanoutQueueOneQueueBindFanoutExchange(@Qualifier(value = "fanoutQueueOne") Queue queue,
                                                         @Qualifier(value = "fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean(value = "fanoutQueueTwoQueueBindFanoutExchange")
    public Binding fanoutQueueTwoQueueBindFanoutExchange(@Qualifier(value = "fanoutQueueTwo") Queue queue,
                                                         @Qualifier(value = "fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean(value = "topicQueue")
    public Queue topicQueue() {
        return QueueBuilder.durable(TOPIC_QUEUE).build();
    }

    @Bean(value = "topicExchange")
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE);
        return topicExchange;
    }

    @Bean(value = "topicQueueBindTopicExchange")
    public Binding topicQueueBindTopicExchange(@Qualifier(value = "topicQueue") Queue queue,
                                               @Qualifier(value = "topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TOPIC_ROUTING_KEY);
    }

    /**
     * 优先级队列、消息生产者设置优先级，0-20
     * 消息放入队列后，消费者再进行消费
     *
     * @return priorityQueue
     */
    @Bean(value = "priorityQueue")
    public Queue priorityQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 20);
        // arguments.put("x-max-length", 6);
        return QueueBuilder.durable(PRIORITY_QUEUE).withArguments(arguments).build();
    }

    @Bean(value = "priorityExchange")
    public DirectExchange priorityExchange() {
        DirectExchange confirmExchange = new DirectExchange(PRIORITY_EXCHANGE);
        return confirmExchange;
    }

    @Bean(value = "confirmQueueBindConfirmExchange")
    public Binding priorityQueueBindPriorityExchange(@Qualifier(value = "priorityQueue") Queue queue,
                                                     @Qualifier(value = "priorityExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(PRIORITY_ROUTING_KEY);
    }


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
                .durable(Boolean.TRUE) // 持久化
                .withArgument("alternate", BACKUP_EXCHANGE) // 备份交换机
                // .alternate(BACKUP_EXCHANGE)
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
