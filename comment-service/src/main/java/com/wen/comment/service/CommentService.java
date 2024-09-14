package com.wen.comment.service;

import com.wen.common.domain.comment.dto.CommentDTO;
import com.wen.common.lang.ResponseVO;

public interface CommentService {
    // 创建评论
    ResponseVO<?> createComment(CommentDTO content);

    // 删除评论
    ResponseVO<?> deleteComment(Long id);
}
