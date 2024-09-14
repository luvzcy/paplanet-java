package com.wen.common.domain.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Topic {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
