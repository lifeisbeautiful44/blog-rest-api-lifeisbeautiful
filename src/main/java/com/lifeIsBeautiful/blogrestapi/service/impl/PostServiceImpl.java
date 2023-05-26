package com.lifeIsBeautiful.blogrestapi.service.impl;

import com.lifeIsBeautiful.blogrestapi.entity.Category;
import com.lifeIsBeautiful.blogrestapi.entity.Post;
import com.lifeIsBeautiful.blogrestapi.exception.ResourceNotFoundException;
import com.lifeIsBeautiful.blogrestapi.payloads.PostDto;
import com.lifeIsBeautiful.blogrestapi.repository.CategoryRepository;
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
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToPost(postDto);

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "category_id", postDto.getCategoryId())
        );

        post.setCategory(category);


        Post savedPost = postRepository.save(post);

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

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "category_id", postDto.getCategoryId())
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        PostDto updatedPostDto = mapToDto(updatedPost);

        return updatedPostDto;

    }

    @Override
    public String deletePostById(Long postId) {

        postRepository.deleteById(postId);

        return "The post with the postId: " + postId + " has been successfully deleted. ";
    }

    @Override
    public List<PostDto> findByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "category_id", categoryId)
        );

        List<Post> postsByCategory = postRepository.findByCategoryId(category.getId());

        List<PostDto> postDtoByCategory = postsByCategory.stream().map(
                        (post) -> mapToDto(post))
                .collect(Collectors.toList());

        return postDtoByCategory;

    }


    //Convert Jpa entity to DTO
    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
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
