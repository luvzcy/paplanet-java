package com.wen.post.mapper;

import com.wen.common.domain.post.ReviewPostLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewPostLogMapper {
    int insert(ReviewPostLog record);
}
