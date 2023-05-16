package com.lifeIsBeautiful.blogrestapi.service;

import com.lifeIsBeautiful.blogrestapi.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto findById(Long postId);

    PostDto updatePost(PostDto postDto,Long postId);

    String deletePostById(Long postId);


}
