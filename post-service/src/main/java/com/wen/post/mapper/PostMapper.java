package com.wen.post.mapper;

import com.wen.common.domain.post.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    // 发布帖子
    int insert(Post post);
    // 管理员审核帖子
    int review(Post post);
    // 修改
    int update(Post post);
    // 根据id查询帖子
    Post selectById(Long id);
    // 查询全部帖子
    List<Post> selectAll();

}
