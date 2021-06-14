package com.bekdaulet.blog_project.controllers;

import com.bekdaulet.blog_project.dtos.CommentDTO;
import com.bekdaulet.blog_project.models.Comment;
import com.bekdaulet.blog_project.models.Post;
import com.bekdaulet.blog_project.models.User;
import com.bekdaulet.blog_project.services.CommentService;
import com.bekdaulet.blog_project.services.UserService;
import com.bekdaulet.blog_project.services.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CommentController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    private ResponseEntity comment(CommentDTO commentDTO){

        Post post = postService.getPostById(commentDTO.getPost_id());
        User user = userService.getCurrentUser();
        if (post != null && user != null) {
            Comment comment = new Comment(null, commentDTO.getContent(), post, user, new Timestamp(System.currentTimeMillis()));
            comment = commentService.addComment(comment);
            if (comment != null){
                List<Comment> comments = post.getComments();
                comments.add(comment);
                post.setComments(comments);
                postService.addPost(post);
                commentDTO.setAuthor(user.getUsername());
                commentDTO.setTime(comment.getCommentDate());
                ResponseEntity<CommentDTO> commentDTOResponseEntity = new ResponseEntity(commentDTO, HttpStatus.OK);
                return commentDTOResponseEntity;
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/comment/{id}")
    private ResponseEntity comment(@PathVariable Long id){
        Comment comment = commentService.getCommentById(id);
        if(comment != null){
            Post post = postService.getPostById(comment.getId());
            List<Comment> comments = post.getComments().stream().filter(c -> c.getId() != comment.getId()).collect(Collectors.toList());
            post.setComments(comments);
            postService.savePost(post);
            User user = userService.getCurrentUser();
            if(user.getId().equals(comment.getAuthor().getId())){
                commentService.deleteComment(comment);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
