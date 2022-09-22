package com.shreyas.blog.service.impl;

import com.shreyas.blog.entity.Post;
import com.shreyas.blog.exception.ResourceNotFoundException;
import com.shreyas.blog.payload.PostDto;
import com.shreyas.blog.payload.PostResponse;
import com.shreyas.blog.repository.PostRepository;
import com.shreyas.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {


        //convert Dto to post
        Post post = mapToPost(postDto);

        Post newPost = postRepository.save(post);

        //convert post to dto for returning

        PostDto postDto1 = mapToDto(newPost);

        return postDto1;

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //sorting by asc or desc order
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


        Page<Post> listOfPage = postRepository.findAll(pageable);

        //get content for page object
        List<Post> allposts = listOfPage.getContent();
        List<PostDto> newPosts = allposts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(newPosts);
        postResponse.setPageNo(listOfPage.getNumber());
        postResponse.setLast(listOfPage.isLast());
        postResponse.setPageSize(listOfPage.getSize());
        postResponse.setTotalElements(listOfPage.getNumberOfElements());
        postResponse.setTotalPages(listOfPage.getTotalPages());


        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        return mapToDto(postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    PostDto mapToDto(Post post) {
        PostDto postDto1 = modelMapper.map(post, PostDto.class);
//        PostDto postDto1 = new PostDto();
//        postDto1.setId(post.getId());
//        postDto1.setTitle(post.getTitle());
//        postDto1.setDescription(post.getDescription());
//        postDto1.setContent(post.getContent());
        return postDto1;
    }

    Post mapToPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
//        post.setId(postDto.getId());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        post.setTitle(postDto.getTitle());
        return post;
    }
}
