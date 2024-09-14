package com.wen.common.domain.comment.dto;

import lombok.Data;

/**
 * 创建评论DTO
 */
@Data
public class CommentDTO {
    private CommentatorDTO commentator; // 评论人信息
    private String contentType; // 评论内容类型
    private Long contentId; // 评论的内容id，可以为帖子id，也可以是回复评论的id
    private String contentText; // 评论内容
    private Long rootId; // 评论的根节点
    private Long repliedToCommentId; // 回复的评论id
    private Long updateBy; // 更新人
    private Integer delFlag; // 删除标记，通常用0表示未删除，1表示已删除
}
