package com.wen.notification.mapper;

import com.wen.common.domain.notification.UserNotification;
import org.apache.ibatis.annotations.Mapper;

import javax.management.MXBean;

@Mapper
public interface UserNotificationMapper {
    int insert(UserNotification notification);
    int delete(UserNotification notification);
}
