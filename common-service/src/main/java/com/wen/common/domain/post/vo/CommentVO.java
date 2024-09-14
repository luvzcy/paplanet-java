package com.wen.common.domain.post.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVO {
    private Long id;
    private Long postId;
    private String content;
    private AuthorVO author; // 评论人信息
    private  AuthorVO replyToUser; // 被评论人信息
    private String createTime; // 格式化后的创建时间字符串，适合前端展示
    private Integer likes; // 点赞数
    private Boolean isLiked; // 当前用户是否已点赞（这可能需要额外的逻辑来判断）
    private List<CommentVO> replies; // 子评论列表，如果需要在同一层级展示

}
