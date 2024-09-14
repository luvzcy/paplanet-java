package com.wen.post.mapper;

import com.wen.common.domain.post.PostTopic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostTopicMapper {
    int insert(List<PostTopic> postTopics);
}
