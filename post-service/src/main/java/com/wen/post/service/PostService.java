package com.wen.post.service;

import com.wen.common.domain.post.dto.LikeDTO;
import com.wen.common.domain.post.dto.PostDTO;
import com.wen.common.domain.post.dto.ReviewPostDTO;
import com.wen.common.lang.ResponseVO;

public interface PostService {
    // 添加帖子
    ResponseVO<?> addPost(PostDTO postDTO);
    // 审核帖子
    ResponseVO<?> reviewPost(ReviewPostDTO reviewPostDTO);
    // 点赞
    ResponseVO<?> likePost(LikeDTO likeDTO);


}
