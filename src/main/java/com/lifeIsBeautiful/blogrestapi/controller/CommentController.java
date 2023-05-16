package com.lifeIsBeautiful.blogrestapi.controller;


import com.lifeIsBeautiful.blogrestapi.payloads.CommentDto;
import com.lifeIsBeautiful.blogrestapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") Long post_id ,@Valid @RequestBody CommentDto commentDto)
    {

       CommentDto newSavedComment =  commentService.createComment(post_id,commentDto);

       return new ResponseEntity<>(newSavedComment, HttpStatus.CREATED);

    }

    @GetMapping("{id}/comments")
    public ResponseEntity<List<CommentDto>> findCommentsByPostId(@PathVariable("id") Long post_id)
    {
       List<CommentDto> commentDtosList =  commentService.findCommentByPostId(post_id);

       return new ResponseEntity<>(commentDtosList, HttpStatus.OK);
    }

    @GetMapping("{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable Long post_id, @PathVariable Long comment_id)
    {
        CommentDto commentDto = commentService.getCommentById(post_id, comment_id);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long post_id,  @PathVariable Long comment_id, @Valid @RequestBody CommentDto commentDto)

    {
       CommentDto updatedComment =  commentService.updateComment(post_id,comment_id,commentDto);

        return  new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    @DeleteMapping("{post_id}/comments/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id)
    {

        commentService.deleteComment(post_id,comment_id);

        return new ResponseEntity<>("The comment with "+ comment_id+ " has been succesfully deleeted",HttpStatus.OK);
    }
}
