package com.wen.common.domain.comment;


import lombok.Data;

@Data
public class CommentMedia {
    private Long id;
    private Long commentId;
    private String mediaType;
    private String mediaUrl;
}
