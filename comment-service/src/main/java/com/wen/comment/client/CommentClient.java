package com.wen.comment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("comment-service")
public interface CommentClient {

    @GetMapping("/comment/user/{commentId}")
    Long getUserByCommentId(@PathVariable("commentId") Long id);

}
