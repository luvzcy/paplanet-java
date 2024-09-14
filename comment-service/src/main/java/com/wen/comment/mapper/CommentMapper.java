package com.wen.comment.mapper;

import com.wen.common.domain.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    int insert(Comment comment);

    Long getUserIdByCommentId(Long repliedToCommentId);

    int deleteById(Long id);
}
