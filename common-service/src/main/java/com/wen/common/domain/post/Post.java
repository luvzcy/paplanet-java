package com.wen.common.domain.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    private Long userId;
    private String content;
    private Long categoryId;
    private Integer views;
    private Integer comments;
    private Integer saves;
    private Integer shares;
    private Integer likes;
    private LocalDateTime createAt;
    private Double location;
    private String type;
    private Integer delFlag;
}

