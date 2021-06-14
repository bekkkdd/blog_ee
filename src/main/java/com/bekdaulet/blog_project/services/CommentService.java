package com.bekdaulet.blog_project.services;

import com.bekdaulet.blog_project.models.Comment;
import com.bekdaulet.blog_project.models.Post;
import com.bekdaulet.blog_project.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByPost(Post post);
    Comment getCommentById(Long id);

    Comment saveComment(Comment comment);
    Comment addComment(Comment comment);

    void deleteComment(Comment comment);
}
