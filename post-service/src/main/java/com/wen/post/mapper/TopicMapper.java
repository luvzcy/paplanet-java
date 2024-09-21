package com.wen.post.mapper;

import com.wen.common.domain.post.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TopicMapper {

    List<String> selectTopicNames(List<Long> ids);
    List<Long> selectTopicIdsByTopicNames(List<String> topicNames);

}
