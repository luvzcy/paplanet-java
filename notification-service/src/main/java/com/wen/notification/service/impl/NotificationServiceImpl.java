package com.wen.notification.service.impl;

import com.wen.common.domain.notification.UserNotification;
import com.wen.notification.service.NotificationService;
import com.wen.notification.service.strategy.NotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private Map<String, NotificationStrategy> strategyMap;

    @Override
    public void handleNotification(UserNotification notification, String action) {
        // 选择适当的策略并执行
        NotificationStrategy strategy = getStrategy(notification.getType());
        System.out.println("通知处理策略:" + strategy.getClass().getSimpleName());
        strategy.handleNotification(notification, action);
    }

    // 获取通知处理策略
    private NotificationStrategy getStrategy(String type) {
        NotificationStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid notification type: " + type);
        }
        return strategy;
    }
}
