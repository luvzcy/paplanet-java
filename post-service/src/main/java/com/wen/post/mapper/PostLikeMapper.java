package com.wen.post.mapper;

import com.wen.common.domain.post.PostLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {
    PostLike selectByPostIdAndUserId(Long postId, Long userId);

    int delete(Long postId, Long userId);

    int insert(PostLike postLike);
}
