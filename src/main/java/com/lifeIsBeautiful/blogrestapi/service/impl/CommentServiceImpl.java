package com.lifeIsBeautiful.blogrestapi.service.impl;

import com.lifeIsBeautiful.blogrestapi.entity.Comment;
import com.lifeIsBeautiful.blogrestapi.entity.Post;
import com.lifeIsBeautiful.blogrestapi.exception.BlogApiException;
import com.lifeIsBeautiful.blogrestapi.exception.ResourceNotFoundException;
import com.lifeIsBeautiful.blogrestapi.payloads.CommentDto;
import com.lifeIsBeautiful.blogrestapi.repository.CommentRepository;
import com.lifeIsBeautiful.blogrestapi.repository.PostRepository;
import com.lifeIsBeautiful.blogrestapi.service.CommentService;
import com.lifeIsBeautiful.blogrestapi.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private ModelMapper mapper;



    @Override
    public CommentDto createComment(Long post_id, CommentDto commentDto) {

        //retrieve post entity by id;
        //System.out.println(post_id);
        Post post = retrievePostById(post_id);

        //converting comment dto to comment jpa entity
        Comment comment = mapToCommentEntity(commentDto);
        /*System.out.println(comment);*/
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
       // System.out.println(newComment);

         //converting comment jpa entity to comment dto
         CommentDto newCommentDto = mapToCommentDTO(comment);

        return newCommentDto;
    }

    @Override
    public List<CommentDto> findCommentByPostId(Long post_id) {
        List<Comment> comments = commentRepository.findByPostId(post_id);

        System.out.println(comments);

       List<CommentDto> commentDto =   comments.stream().map(
                comment -> mapToCommentDTO(comment)
       ).collect(Collectors.toList());

        return commentDto;
    }

    @Override
    public CommentDto getCommentById(Long post_id, Long comment_id) {

        //Retrieve Post By id
        Post post = retrievePostById(post_id);

        //Retrieve Comment by id
        Comment comment = retrieveCommentById(comment_id);

        //Check whether particular comment belongs to post or not

        if(!post.getId().equals(comment.getPost().getId()))
        {
           throw   new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post with post_id: " +post_id);
        }

        CommentDto commentDto = mapToCommentDTO(comment);

        return commentDto;

    }

    @Override
    public CommentDto updateComment(Long post_id, Long comment_id, CommentDto commentDto) {

        //retrive post by post id
        Post post = retrievePostById(post_id);

        //retrieve comment by comment id
        Comment comment = retrieveCommentById(comment_id);

        if(!post.getId().equals(comment.getPost().getId()))
        {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Comment with comment id: "+ comment_id+ " does not belongs to the particular post: " +post_id);
        }

        comment.setName(commentDto.getName());
        comment.setEmail(comment.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        CommentDto updatedCommentDto = mapToCommentDTO(comment);

        return updatedCommentDto;
    }

    @Override
    public void deleteComment(Long post_id, Long comment_id) {
        //retrive post by post id
        Post post = retrievePostById(post_id);

        //retrieve comment by comment id
        Comment comment = retrieveCommentById(comment_id);

        //Check whether the particular comment belongs to post

        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment with comment id: "+ comment_id+ " does not belongs to the particular post: " +post_id);
        }
        commentRepository.delete(comment);
    }


    private CommentDto mapToCommentDTO(Comment comment)
    {
        CommentDto commentDto = mapper.map(comment,CommentDto.class);

        return commentDto;

    }

    private Comment mapToCommentEntity(CommentDto commentDto)
    {
        Comment comment = mapper.map(commentDto,Comment.class);
        return comment;
    }

    private Post retrievePostById(Long post_id)
    {
      Post post =   postRepository.findById(post_id).orElseThrow(
                () ->   new ResourceNotFoundException("Post","post_id",post_id)
                );

      return post;
    }

    private Comment retrieveCommentById(Long comment_id)
    {
       Comment comment =commentRepository.findById(comment_id).orElseThrow(
                () -> new ResourceNotFoundException("Comments", "comment_id", comment_id)
        );
       return comment;
    }


}
