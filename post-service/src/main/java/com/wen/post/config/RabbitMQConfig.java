package com.wen.post.config;

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

    // 定义点赞和取消点赞通知的通用队列
    @Bean
    public Queue likeNotificationQueue() {
        return new Queue("likeNotificationQueue");
    }

    // 将点赞通知和取消点赞通知队列绑定到交换机，匹配"notification.like"和"notification.cancel-like"
    @Bean
    public Binding likeBinding(Queue likeNotificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(likeNotificationQueue).to(notificationExchange).with("notification.like");
    }

    @Bean
    public Binding cancelLikeBinding(Queue likeNotificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(likeNotificationQueue).to(notificationExchange).with("notification.cancel-like");
    }
}

