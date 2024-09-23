package com.wen.common.domain.post.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MediaVO {
    private List<String> urls = new ArrayList<>();
    private String type;//媒体类型
    private String videoId;    // 视频ID (仅当 type 为 VIDEO 时)
    private String playAuth;   // 视频播放凭证 (仅当 type 为 VIDEO 时)
}
