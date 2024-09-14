package com.wen.comment.service.impl;

import com.wen.common.domain.notification.NotificationAction;
import com.wen.common.domain.notification.dto.CommentNotificationDTO;
import com.wen.common.utils.KryoUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private TopicExchange notificationExchange; // 自动注入配置类中定义的主题交换机

    private static final String COMMENT_ROUTING_KEY = "notification.comment"; // 评论通知路由键
    private static final String DELETE_COMMENT_ROUTING_KEY = "notification.delete-comment"; // 删除评论通知路由键

    /**
     * 发送评论通知消息到 RabbitMQ
     */
    public void sendCommentNotification(CommentNotificationDTO notification) {

        // 将 CommentNotificationDTO 对象序列化为字符串
        String serializedNotification = KryoUtil.serializeToString(notification);

        // 获取路由键，根据操作类型决定发送到哪个路由
        String routingKey = getRoutingKeyByAction(notification.getAction());

        // 发送序列化后的字符串，使用动态路由键
        rabbitTemplate.convertAndSend(notificationExchange.getName(), routingKey, serializedNotification);
    }

    /**
     * 根据通知内容确定路由键
     */
    private String getRoutingKeyByAction(String action) {
        if (NotificationAction.ADD.name().equals(action)) {
            return COMMENT_ROUTING_KEY;
        } else if (NotificationAction.DELETE.name().equals(action)) {
            return DELETE_COMMENT_ROUTING_KEY;
        }
        throw new IllegalArgumentException("Unknown notification action: " + action);
    }
}
