package com.lifeIsBeautiful.blogrestapi.controller;


import com.lifeIsBeautiful.blogrestapi.payloads.PostDto;
import com.lifeIsBeautiful.blogrestapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {

        System.out.println(postDto);
        PostDto savedPostDto = postService.createPost(postDto);

        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {

        List<PostDto> allPostsDto = postService.getAllPosts();
        return new ResponseEntity<>(allPostsDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> findPostById(@PathVariable(name = "id") Long postId) {
        System.out.println(postId);
        PostDto postDto = postService.findById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long postId, @Valid @RequestBody PostDto postDto) {

        PostDto updatedPostDto = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") Long postId) {
        String message = postService.deletePostById(postId);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
