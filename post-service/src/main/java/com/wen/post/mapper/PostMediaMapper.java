package com.wen.post.mapper;

import com.wen.common.domain.post.PostMedia;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMediaMapper {
    int insert(List<PostMedia> postMedias);
    List<PostMedia> selectByPostId(Long id);
}
