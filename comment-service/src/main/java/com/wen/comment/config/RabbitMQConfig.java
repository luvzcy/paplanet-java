package com.wen.comment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 定义主题交换机
    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange("notificationExchange");
    }

    // 定义评论与删除评论通知的通用队列
    @Bean
    public Queue commentNotificationQueue() {
        return new Queue("commentNotificationQueue");
    }

    // 定义@好友通知队列
    @Bean
    public Queue mentionNotificationQueue() {
        return new Queue("mentionNotificationQueue");
    }

    // 将评论通知队列绑定到交换机，匹配"notification.comment"
    @Bean
    public Binding commentBinding(Queue commentNotificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(commentNotificationQueue).to(notificationExchange).with("notification.comment");
    }

    // 将删除评论通知队列绑定到交换机，匹配"notification.delete-comment"
    @Bean
    public Binding deleteCommentBinding(Queue commentNotificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(commentNotificationQueue).to(notificationExchange).with("notification.delete-comment");
    }

    // 将@好友通知队列绑定到交换机，匹配"notification.mention"
    @Bean
    public Binding mentionBinding(Queue mentionNotificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(mentionNotificationQueue).to(notificationExchange).with("notification.mention");
    }
}


