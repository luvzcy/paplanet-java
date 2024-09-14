package com.wen.notification.config;

import org.springframework.amqp.core.*;
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

    // 定义评论与删除评论通知的通用队列
    @Bean
    public Queue commentNotificationQueue() {
        return new Queue("commentNotificationQueue");
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

    // 定义@好友通知队列
    @Bean
    public Queue mentionNotificationQueue() {
        return new Queue("mentionNotificationQueue");
    }

    // 将@好友通知队列绑定到交换机，匹配"notification.mention"
    @Bean
    public Binding mentionBinding(Queue mentionNotificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(mentionNotificationQueue).to(notificationExchange).with("notification.mention");
    }

}
