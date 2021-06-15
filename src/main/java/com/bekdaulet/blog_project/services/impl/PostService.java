package com.bekdaulet.blog_project.services.impl;

import com.bekdaulet.blog_project.models.Comment;
import com.bekdaulet.blog_project.models.Post;
import com.bekdaulet.blog_project.models.User;
import com.bekdaulet.blog_project.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getAllPostsByAuthor(User user) {
        return postRepository.findAllByAuthor(user);
    }

    public List<Post> getAllPostsByAuthors(Collection<User> authors) {
        return postRepository.findByAuthorIds(authors.stream().map(User::getId).collect(Collectors.toList()));
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
