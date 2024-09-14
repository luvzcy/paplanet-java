package com.wen.post;

import com.alibaba.fastjson.JSONObject;
import com.wen.common.domain.notification.UserNotification;
import com.wen.common.utils.KryoUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class PostServiceApplicationTests {

	@Resource
	private RabbitTemplate rabbitTemplate;

	private static final String EXCHANGE_NAME = "like.direct";
	private static final String LIKE_ROUTING_KEY = "like_notification";
	private static final String CANCEL_LIKE_ROUTING_KEY = "cancel_like_notification";

	@Test
	void contextLoads() {
	}

	@Test
	public void testSimpleQueue() {

	}


	/**
	 * 发送通知消息到 RabbitMQ
	 */
	@Test
	public void sendNotification() {

		UserNotification notification = createNotification();
		// 将UserNotification对象序列化为字符串
		String serializedNotification = KryoUtil.serializeToString(notification);

		// 发送序列化后的字符串
		rabbitTemplate.convertAndSend(EXCHANGE_NAME, LIKE_ROUTING_KEY, serializedNotification);
	}

	/**
	 * 创建通知对象
	 */
	private UserNotification createNotification() {
		JSONObject content = new JSONObject();
		UserNotification notification = new UserNotification();
		notification.setTargetUserId(2L);
		notification.setType("like");
		notification.setContent(content.toJSONString());
		notification.setIsRead(0);  // 未读
		notification.setCreatedAt(LocalDateTime.now());
		notification.setUpdatedAt(LocalDateTime.now());
		return notification;
	}
}
