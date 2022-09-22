package com.shreyas.blog.service;

import com.shreyas.blog.payload.PostDto;
import com.shreyas.blog.payload.PostResponse;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(long id);
}
