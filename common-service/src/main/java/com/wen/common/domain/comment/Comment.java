package com.wen.common.domain.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long userId;
    private String contentType;
    private Long contentId;
    private String contentText;
    private Long rootId;
    private Integer likes;
    private Long repliedToCommentId;
    private LocalDateTime createAt; // 创建时间
    private Long updateBy; // 更新人
    private LocalDateTime updateAt; // 更新时间
    private Integer delFlag;
}
