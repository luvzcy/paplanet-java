package com.wen.post.controller;

import com.wen.common.domain.post.dto.LikeDTO;
import com.wen.common.domain.post.dto.PostDTO;
import com.wen.common.domain.post.dto.ReviewPostDTO;
import com.wen.common.lang.ResponseVO;
import com.wen.post.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //添加帖子(未审核)
    @PostMapping(value="/add")
    public ResponseVO<?> addPost(@RequestBody PostDTO postDTO){
        return postService.addPost(postDTO);
    }

    //审核帖子
    @PostMapping(value = "/review")
    public ResponseVO<?> auditPost(@RequestBody ReviewPostDTO reviewPostDTO) {
        return postService.reviewPost(reviewPostDTO);
    }

    // 模拟推荐帖子（无推荐算法）
    @GetMapping(value = "/recommendTest")
    public ResponseVO<?> recommendPost(@RequestParam(value = "userId") Long userId) {
        return postService.testRecommendPost(userId);
    }

    // ToDo 删除帖子
    @DeleteMapping(value = "/{id}")
    public ResponseVO<?> deletePost(@PathVariable Long id) {
        return null;
    }

    // ToDo 修改帖子
    @PutMapping(value = "/{id}")
    public ResponseVO<?> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        return null;
    }

    // ToDo 查询帖子
    @GetMapping(value = "/{id}")
    public ResponseVO<?> getPostById(@PathVariable Long id) {
        return null;
    }

    // ToDo 查询所有帖子
    @GetMapping(value = "/all")
    public ResponseVO<?> getAllPosts() {
        return null;
    }

    // ToDo 帖子推荐
    // 假设基于简单的热门帖子推荐
    @GetMapping(value = "/recommended")
    public ResponseVO<?> getRecommendedPosts() {
        return null;
    }

    // 点赞
    @PostMapping(value = "/like")
    public ResponseVO<?> likePost(@RequestBody LikeDTO likeDTO) {
        return postService.likePost(likeDTO);
    }


}
