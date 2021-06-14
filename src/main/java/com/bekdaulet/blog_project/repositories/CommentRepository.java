package com.bekdaulet.blog_project.repositories;

import com.bekdaulet.blog_project.models.Comment;
import com.bekdaulet.blog_project.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
