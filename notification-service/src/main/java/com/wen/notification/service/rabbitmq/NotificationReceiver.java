package com.wen.notification.service.rabbitmq;

import com.wen.common.domain.notification.UserNotification;
import com.wen.common.domain.notification.dto.CommentNotificationDTO;
import com.wen.common.domain.notification.dto.LikePostNotificationDTO;
import com.wen.common.utils.KryoUtil;
import com.wen.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationReceiver {


    @Autowired
    private NotificationService notificationService;

    // 监听点赞与取消点赞通知队列
    @RabbitListener(queues = "likeNotificationQueue")
    public void receiveLikeNotification(String message) {
        handleLikeNotification(message);
    }


    private void handleLikeNotification(String message) {

        // 将消息转换为 LikePostNotificationDTO 对象
        LikePostNotificationDTO likePostNotificationDTO = KryoUtil.deserializeFromString(message, LikePostNotificationDTO.class);
        // 操作行为（添加or删除）
        String action = likePostNotificationDTO.getAction();
        // 获取通知对象
        UserNotification notification = likePostNotificationDTO.getNotification();
        // 调用 NotificationService 处理通知
        notificationService.handleNotification(notification, action);

    }

    // 监听评论与取消评论通知队列
    @RabbitListener(queues = "commentNotificationQueue")
    public void receiveCommentNotification(String message) {
        handleCreateCommentNotification(message);
    }

    private void handleCreateCommentNotification(String message) {
        // 获取通知对象
        CommentNotificationDTO commentNotificationDTO = KryoUtil.deserializeFromString(message, CommentNotificationDTO.class);
        // 操作行为（添加or删除）
        String action = commentNotificationDTO.getAction();
        UserNotification notification = commentNotificationDTO.getNotification();

        // 调用 NotificationService 处理通知
        notificationService.handleNotification(notification, action);
    }
}
