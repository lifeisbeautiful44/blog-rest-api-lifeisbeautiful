package com.lifeIsBeautiful.blogrestapi.service;

import com.lifeIsBeautiful.blogrestapi.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long post_id, CommentDto commentDto);

    List<CommentDto> findCommentByPostId(Long post_id);

    CommentDto getCommentById(Long post_id , Long comment_id);

    CommentDto updateComment(Long post_id, Long comment_id, CommentDto commentDto);

    void deleteComment(Long post_id, Long comment_id);
}
