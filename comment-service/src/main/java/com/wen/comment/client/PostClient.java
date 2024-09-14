package com.wen.comment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service")
public interface PostClient {
    @GetMapping("/post/user/{postId}")
    Long getUserByPostId(@PathVariable("postId") Long id);
}
