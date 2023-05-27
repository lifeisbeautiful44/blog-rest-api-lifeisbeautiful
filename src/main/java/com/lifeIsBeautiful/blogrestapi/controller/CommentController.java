package com.lifeIsBeautiful.blogrestapi.controller;


import com.lifeIsBeautiful.blogrestapi.payloads.CommentDto;
import com.lifeIsBeautiful.blogrestapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
@RestController
@RequestMapping("/api/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save comment to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    // Build Create Comment RESTAPI
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") Long post_id ,@Valid @RequestBody CommentDto commentDto)
    {

       CommentDto newSavedComment =  commentService.createComment(post_id,commentDto);

       return new ResponseEntity<>(newSavedComment, HttpStatus.CREATED);

    }

    @Operation(
            summary = "Get  Comment REST API",
            description = "Get All Comment REST API is used to fetch all the comments that belongs to particular posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    // Build Get Comment By Post RESTAPI
    @GetMapping("{id}/comments")
    public ResponseEntity<List<CommentDto>> findCommentsByPostId(@PathVariable("id") Long post_id)
    {
       List<CommentDto> commentDtosList =  commentService.findCommentByPostId(post_id);

       return new ResponseEntity<>(commentDtosList, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Comment By ID REST API",
            description = "Get  Comment By ID REST API is used to a get single comment of the post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    //Build Get Comment By ID RESTAPI
    @GetMapping("{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable Long post_id, @PathVariable Long comment_id)
    {
        CommentDto commentDto = commentService.getCommentById(post_id, comment_id);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment REST API",
            description = "Update Comment  REST API is used to update a particular comment that belongs to particular post in the database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    // Build Update Comment RESTAPI
    @PutMapping("{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long post_id,  @PathVariable Long comment_id, @Valid @RequestBody CommentDto commentDto)

    {
       CommentDto updatedComment =  commentService.updateComment(post_id,comment_id,commentDto);

        return  new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment REST API",
            description = "Delete Comment Post  REST API is used to delete a particular comment that belongs to particular post in the database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    // Delete Comment RestAPI
    @DeleteMapping("{post_id}/comments/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id)
    {

        commentService.deleteComment(post_id,comment_id);

        return new ResponseEntity<>("The comment with "+ comment_id+ " has been succesfully deleeted",HttpStatus.OK);
    }
}
