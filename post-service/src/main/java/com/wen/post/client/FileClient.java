package com.wen.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "file-service")
public interface FileClient {

    //根据VideoId获取播放视频地址
    @GetMapping("/vdo/getPlayAuth")
    Map<String, Object> getPlayAuth(@RequestParam("videoId") String videoId);

}
