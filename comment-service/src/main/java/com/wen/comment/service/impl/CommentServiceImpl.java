package com.wen.comment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wen.comment.client.CommentClient;
import com.wen.comment.client.PostClient;
import com.wen.comment.mapper.CommentMapper;
import com.wen.comment.service.CommentService;
import com.wen.common.domain.auth.vo.UserinfoVO;
import com.wen.common.domain.comment.Comment;
import com.wen.common.domain.comment.dto.CommentDTO;
import com.wen.common.domain.notification.NotificationAction;
import com.wen.common.domain.notification.UserNotification;
import com.wen.common.domain.notification.dto.CommentNotificationDTO;
import com.wen.common.domain.notification.dto.LikePostNotificationDTO;
import com.wen.common.domain.post.Post;
import com.wen.common.lang.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    NotificationService notificationService;

    @Resource
    PostClient postClient;



    // 发表评论
    @Override
    public ResponseVO<?> createComment(CommentDTO contentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(contentDTO, comment);

        // 发布评论
        int insert = commentMapper.insert(comment);
        if (insert > 0) {
            // 封装评论通知
            UserNotification commentNotification = createCommentNotification(contentDTO);
            CommentNotificationDTO commentNotificationDTO = new CommentNotificationDTO();
            commentNotificationDTO.setAction(NotificationAction.ADD.name());
            commentNotificationDTO.setNotification(commentNotification);
            notificationService.sendCommentNotification(commentNotificationDTO);

            return ResponseVO.success();
        }

        return ResponseVO.failure();
    }

    // 删除评论
    @Override
    public ResponseVO<?> deleteComment(Long id) {
        /*
         * 需要删除评论表的数据，通知表的评论数据也删除，两个地方都是伪删除
         * 删除评论表需要评论的id
         * 删除评论通知表需要评论的id(target_id)以及通知类型（target_type=”comment“）
         */
        // 1.删除评论表的数据
        int i = commentMapper.deleteById(id);
        if (i > 0) {
            // 2.删除评论通知表的数据
            // 2.1 封装删除通知的参数
            UserNotification commentNotification = createDeleteCommentNotification(id);
            CommentNotificationDTO commentNotificationDTO = new CommentNotificationDTO();
            commentNotificationDTO.setAction(NotificationAction.DELETE.name());
            commentNotificationDTO.setNotification(commentNotification);
            notificationService.sendCommentNotification(commentNotificationDTO);

            return ResponseVO.success();
        }

        return null;
    }

    /**
     * 创建通知对象
     */
    private UserNotification createCommentNotification(CommentDTO contentDTO) {
        UserNotification notification = new UserNotification();
        JSONObject content = createNotificationContent(contentDTO);

        Long targetUserId = getTargetUserId(contentDTO);
        notification.setTargetUserId(targetUserId);
        notification.setContent(content.toJSONString());
        notification.setTargetId(contentDTO.getContentId());
        notification.setTargetType("comment"); //也是用于区分通知类型，但是用来判断TargetId是评论还是帖子还是文章等，方便删除通知表的数据
        notification.setIsRead(0);  // 未读
        notification.setType("comment"); // 通知类型
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        return notification;
    }

    /**
     * 创建通知内容
     */
    private JSONObject createNotificationContent(CommentDTO contentDTO) {
        JSONObject content = new JSONObject();
        content.put("user_nickname", contentDTO.getCommentator().getNickname());
        content.put("user_avatar", contentDTO.getCommentator().getAvatar());

        String notificationMessage;
        String commentContent = contentDTO.getContentText();
        if (commentContent.length() > 10) {
            commentContent = commentContent.substring(0, 10) + "...";
        }

        /*
         * 设计通知的用户时，要判断时在哪评论的，有帖子下的评论、文章下的评论或者回复评论的评论等等，具体可分两大类为：
         * 1.帖子、文章或视频下的评论归为一类，targetUserId直接设置为帖子、文章或视频的作者id；
         *   而通知的内容就分别可以表示为：某人在***作品下评论了你。
         * 2.回复别人的评论归为一类，targetUserId设置为被回复的用户id；
         *   而通知的内容就分别可以表示为：你在***作品的评论被某回复了。
         */
        if (contentDTO.getRootId() == -1) {
            // 第一类：帖子、文章或视频下的评论
            notificationMessage = contentDTO.getCommentator().getNickname() + "在我的作品中发表了评论";
        } else {
            // 第二类：回复别人的评论
            notificationMessage = contentDTO.getCommentator().getNickname() + "回复了我的评论";
        }

        content.put("notification_message", notificationMessage);
        content.put("comment_content", commentContent);
        return content;
    }

    /**
     * 获取目标用户 ID
     */
    private Long getTargetUserId(CommentDTO contentDTO) {
        Long targetUserId = null;
        if (contentDTO.getRootId() == -1) {
            // 第一类：帖子、文章或视频下的评论，直接通过帖子或文章等获取作者id
            if ("post".equals(contentDTO.getContentType())) {
                targetUserId = postClient.getUserByPostId(contentDTO.getContentId());
            }else {
                throw new RuntimeException("未知的评论类型");
            }
        } else {
            // 第二类：回复评论，直接通过评论表获取被回复的用户id
            targetUserId = commentMapper.getUserIdByCommentId(contentDTO.getRepliedToCommentId());

        }
        return targetUserId;
    }

    private UserNotification createDeleteCommentNotification(Long id) {
        UserNotification notification = new UserNotification();
        notification.setType("comment"); //用于区分通知类型
        notification.setTargetId(id);
        notification.setTargetType("comment"); //也是用于区分通知类型，但是用来判断TargetId是评论还是帖子还是文章等，方便删除通知表的数据
        return notification;
    }

}
