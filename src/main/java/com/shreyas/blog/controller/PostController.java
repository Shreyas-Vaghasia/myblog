package com.shreyas.blog.controller;

import com.shreyas.blog.payload.PostDto;
import com.shreyas.blog.payload.PostResponse;
import com.shreyas.blog.service.PostService;
import com.shreyas.blog.utils.AppConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity(postService.createPost(postDto), CREATED);
    }

    @GetMapping
    public PostResponse getAllposts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORTY_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir

    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPostById(@PathVariable long id) {
        return ResponseEntity.ok(postService.getPostById(id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity updatePost(@RequestBody PostDto postDto, @PathVariable long id) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable long id) {

        postService.deletePost(id);
        return ResponseEntity.ok("Post Deleted Succesfully");
    }
}
