package com.wen.common.domain.post.vo;

import lombok.Data;

import java.util.List;

@Data
public class PostVO {
    private Long userId;
    private String content;
    private String category;
    private List<String> topics;
    private Double location;
    private String type;
    private MediaVO media;
    private AuthorVO author;
}
