package com.shreyas.blog.service;

import com.shreyas.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long id, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long id);

    CommentDto getCommentbyId(long pid, long cid);

    void deleteCommentById(long pid, long cid);

    CommentDto updateCommentById(long pid, long cid, CommentDto commentDto);
}
