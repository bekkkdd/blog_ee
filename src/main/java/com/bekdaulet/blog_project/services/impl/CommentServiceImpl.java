package com.bekdaulet.blog_project.services.impl;

import com.bekdaulet.blog_project.models.Comment;
import com.bekdaulet.blog_project.models.Post;
import com.bekdaulet.blog_project.repositories.CommentRepository;
import com.bekdaulet.blog_project.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }
    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
