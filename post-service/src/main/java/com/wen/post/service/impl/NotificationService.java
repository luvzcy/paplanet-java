package com.wen.post.service.impl;

import com.wen.common.domain.notification.NotificationAction;
import com.wen.common.domain.notification.dto.LikePostNotificationDTO;
import com.wen.common.utils.KryoUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private TopicExchange notificationExchange; // 自动注入配置类中定义的主题交换机

    private static final String LIKE_ROUTING_KEY = "notification.like"; // 点赞路由键
    private static final String CANCEL_LIKE_ROUTING_KEY = "notification.cancel-like"; // 取消点赞路由键

    /**
     * 发送通知消息到 RabbitMQ
     */
    public void sendNotification(LikePostNotificationDTO notification) {

        System.out.println("sendNotification: " + notification);

        // 将UserNotification对象序列化为字符串
        String serializedNotification = KryoUtil.serializeToString(notification);

        // 获取路由键
        String routingKey = getRoutingKeyByAction(notification.getAction());

        // 发送序列化后的字符串
        rabbitTemplate.convertAndSend(notificationExchange.getName(),routingKey, serializedNotification);//参数分别为交换机名，路由键，消息

    }

    /**
     * 根据通知内容确定路由键
     */
    private String getRoutingKeyByAction(String action) {

        if (NotificationAction.ADD.name().equals(action)) {
            return LIKE_ROUTING_KEY;
        }
        return CANCEL_LIKE_ROUTING_KEY;

    }
}
