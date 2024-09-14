package com.wen.common.domain.post.vo;

import lombok.Data;

import java.util.List;

@Data
public class MediaVO {
    private List<String> url;//媒体地址
    private String type;//媒体类型
}
