package com.wen.post.mapper;

import com.wen.common.domain.post.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    Category selectById(Long id);
}
