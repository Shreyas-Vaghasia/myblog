package com.shreyas.blog.service.impl;

import com.shreyas.blog.entity.Comment;
import com.shreyas.blog.entity.Post;
import com.shreyas.blog.exception.ResourceNotFoundException;
import com.shreyas.blog.payload.CommentDto;
import com.shreyas.blog.repository.CommentRepository;
import com.shreyas.blog.repository.PostRepository;
import com.shreyas.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {
        //find the post using the id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        //convert the DTO to Comment for saving into post
        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        //save comment in DB
        Comment savedComment = commentRepository.save(comment);

        return mapTODto(savedComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long id) {
        //find all the comments in the table
        List<Comment> allComments = commentRepository.findAll();
//        System.out.println(allComments);

        //filter the comments by matching id
        List<Comment> filteredComments = allComments.stream().filter(c -> c.getPost().getId().equals(id)).collect(Collectors.toList());


        List<CommentDto> result = filteredComments.stream().map(c -> mapTODto(c)).collect(Collectors.toList());

        return result;
    }

    @Override
    public CommentDto getCommentbyId(long pid, long cid) {

        //find all the comments in the table
        List<Comment> allComments = commentRepository.findAll();

        //get all the post with pid
        List<Comment> allPostWithPid = allComments.stream().filter(c -> c.getPost().getId().equals(pid)).collect(Collectors.toList());

        //find the comment by comment id
        Comment filteredComment = allPostWithPid.stream().filter(c -> c.getId().equals(cid)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Comment", "id", cid));


        return mapTODto(filteredComment);
    }

    @Override
    public void deleteCommentById(long pid, long cid) {

        //find all the comments in the table
        List<Comment> allComments = commentRepository.findAll();

        //get all the post with pid
        List<Comment> allPostWithPid = allComments.stream().filter(c -> c.getPost().getId().equals(pid)).collect(Collectors.toList());
        //find the comment by comment id
        Comment filteredComment = allPostWithPid.stream().filter(c -> c.getId().equals(cid)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Comment", "id", cid));

        commentRepository.delete(filteredComment);

    }

    @Override
    public CommentDto updateCommentById(long pid, long cid, CommentDto commentDto) {

        //find all the comments in the table
        List<Comment> allComments = commentRepository.findAll();

        //get all the post with pid
        List<Comment> allPostWithPid = allComments.stream().filter(c -> c.getPost().getId().equals(pid)).collect(Collectors.toList());
        //find the comment by comment id
        Comment filteredComment = allPostWithPid.stream().filter(c -> c.getId().equals(cid)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Comment", "id", cid));


        //Create new COmment object to be update in Table
        Comment updatedComment = new Comment();
        updatedComment.setId(commentDto.getId());
        updatedComment.setPost(filteredComment.getPost());
        updatedComment.setName(commentDto.getName());
        updatedComment.setEmail(commentDto.getEmail());
        updatedComment.setBody(commentDto.getBody());

        //saving comment to table
        Comment savedComment = commentRepository.save(updatedComment);


        return mapTODto(savedComment);
    }

    Comment mapToComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto,Comment.class);
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setName(commentDto.getName());
//        comment.setId(commentDto.getId());
        return comment;
    }

    CommentDto mapTODto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
//        commentDto.setId(comment.getId());
        return commentDto;
    }


}
