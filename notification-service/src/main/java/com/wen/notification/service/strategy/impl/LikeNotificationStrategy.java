package com.wen.notification.service.strategy.impl;

import com.wen.common.domain.notification.UserNotification;
import com.wen.notification.mapper.UserNotificationMapper;
import com.wen.notification.service.strategy.NotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component("like")
public class LikeNotificationStrategy implements NotificationStrategy {

    @Autowired
    private UserNotificationMapper userNotificationMapper;

    /*
     * 带有action参数的方法，用于点赞逻辑
     */
    @Transactional
    @Override
    public void handleNotification(UserNotification notification, String action) {
        switch (action) {
            case "ADD" -> {
                // 处理点赞逻辑
                int insert = userNotificationMapper.insert(notification);
                if (insert <= 0) {
                    throw new RuntimeException("保存点赞记录失败");
                }
            }
            case "DELETE" -> {
                // 处理取消点赞逻辑
                int delete = userNotificationMapper.delete(notification);
                if (delete <= 0) {
                    throw new RuntimeException("删除点赞记录失败");
                }
            }
            default -> throw new IllegalArgumentException("Invalid action: " + action);
        }
    }


}
