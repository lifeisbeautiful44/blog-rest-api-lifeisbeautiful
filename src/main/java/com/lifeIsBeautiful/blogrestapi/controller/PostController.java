package com.lifeIsBeautiful.blogrestapi.controller;


import com.lifeIsBeautiful.blogrestapi.payloads.PostDto;
import com.lifeIsBeautiful.blogrestapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Post Resource"
)
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //Enables authorization header for this particular RestApi
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    //Build Create Post RestApi
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {

        PostDto savedPostDto = postService.createPost(postDto);

        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);

    }

    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Post REST API is used to fetch all the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    //Build Get All Posts RestApi
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {

        List<PostDto> allPostsDto = postService.getAllPosts();
        return new ResponseEntity<>(allPostsDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post By ID REST API",
            description = "Get  Post By ID REST API is used to a get single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    //Build Get Post By Id RestApi
    @GetMapping("{id}")
    public ResponseEntity<PostDto> findPostById(@PathVariable(name = "id") Long postId) {
        System.out.println(postId);
        PostDto postDto = postService.findById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Post REST API",
            description = "Update Post  REST API is used to update a particular post in the database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    //Build Update Post Rest API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long postId, @Valid @RequestBody PostDto postDto) {

        PostDto updatedPostDto = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);

    }

    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post  REST API is used to delete a particular post in the database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    //Build Delete Post RESTAPI
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") Long postId) {
        String message = postService.deletePostById(postId);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //Build Post by category rest api
    @Operation(
            summary = "Get Post with Category REST API",
            description = "Get Post with Category REST API is used to retrieve the particular post that belongs to the category "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    //Get ALL Posts That belongs to particular Category
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> findPostByCategory(@PathVariable("id") Long categoryId)
    {
       List<PostDto> postByCategory = postService.findByCategory(categoryId);

       return new ResponseEntity<>(postByCategory, HttpStatus.OK);

    }
}
