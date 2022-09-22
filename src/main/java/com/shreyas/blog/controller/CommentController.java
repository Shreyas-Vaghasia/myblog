package com.shreyas.blog.controller;

import com.shreyas.blog.entity.Comment;
import com.shreyas.blog.payload.CommentDto;
import com.shreyas.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //Create a new Comment
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment( @PathVariable long id,@Valid @RequestBody CommentDto commentDto) {


        return new ResponseEntity<CommentDto>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    //GET all comments of a post by id
    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable long id) {
        return commentService.getAllCommentsByPostId(id);
    }
    //GET comment by id
    @GetMapping("/posts/{pid}/comments/{cid}")
    public ResponseEntity<CommentDto> getCommentbyId(@PathVariable long pid, @PathVariable long cid) {
        return new ResponseEntity(commentService.getCommentbyId(pid, cid), HttpStatus.OK);
    }
    //Delete comment by id
    @DeleteMapping("/posts/{pid}/comments/{cid}")
    public ResponseEntity deleteCommentById(@PathVariable long pid, @PathVariable long cid) {
        commentService.deleteCommentById(pid, cid);
        return ResponseEntity.ok("Delted successfully");
    }
    //update comment by id
    @PutMapping("/posts/{pid}/comments/{cid}")
    public ResponseEntity<CommentDto> updateCommentById(@Valid @PathVariable long pid, @PathVariable long cid, @RequestBody CommentDto commentDto) {
        return new ResponseEntity(commentService.updateCommentById(pid, cid, commentDto), HttpStatus.OK);
    }
}
