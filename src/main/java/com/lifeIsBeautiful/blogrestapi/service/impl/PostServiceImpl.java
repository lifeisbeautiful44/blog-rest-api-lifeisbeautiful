package com.lifeIsBeautiful.blogrestapi.service.impl;

import com.lifeIsBeautiful.blogrestapi.entity.Post;
import com.lifeIsBeautiful.blogrestapi.exception.ResourceNotFoundException;
import com.lifeIsBeautiful.blogrestapi.payloads.PostDto;
import com.lifeIsBeautiful.blogrestapi.repository.PostRepository;
import com.lifeIsBeautiful.blogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
   private ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToPost(postDto);

        System.out.println(post);
        Post savedPost = postRepository.save(post);
        System.out.println(savedPost);

        PostDto savedPostDto = mapToDto(savedPost);

        return savedPostDto;


    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDto> postDto = posts.stream().map(
                post -> mapToDto(post)).collect(Collectors.toList()
        );

        return postDto;
    }

    @Override
    public PostDto findById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );
        PostDto postDto = mapToDto(post);
        return postDto;

    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        PostDto updatedPostDto = mapToDto(updatedPost);

        return updatedPostDto;

    }

    @Override
    public String deletePostById(Long postId) {

        postRepository.deleteById(postId);

        return "The post with the postId: " + postId + " has been successfully deleted. ";
    }


    //Convert Jpa entity to DTO
    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post,PostDto.class);
       //System.out.println(postDto);
        return postDto;

    }

    //Convert Dto to Jpa entity
    private Post mapToPost(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
      /*  post.setId(post.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
*/
        return post;
    }


}
