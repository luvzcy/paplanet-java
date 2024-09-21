package com.wen.common.domain.post;

import lombok.Data;

@Data
public class PostMedia {
    private Long id;
    private Long postId;
    private String type;
    private String url;
    private String videoId;
}
