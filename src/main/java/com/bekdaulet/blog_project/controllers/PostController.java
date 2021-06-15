package com.bekdaulet.blog_project.controllers;

import com.bekdaulet.blog_project.models.Post;
import com.bekdaulet.blog_project.models.User;
import com.bekdaulet.blog_project.services.impl.PostService;
import com.bekdaulet.blog_project.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public String posts() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String posts(
            Model model
    ) {
        List<Post> posts = postService.getAllPostsByAuthors(userServiceImpl.getCurrentUser().getChannels());
        model.addAttribute("posty", posts);
        return "posts";
    }

    @GetMapping("/posts/new")
    public String newPost(
            Model model
    ) {
        return "newPost";
    }

    @PostMapping("/posts/new")
    public String newPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile img
    ) {
        User author = userServiceImpl.getCurrentUser();
        if (author == null) {
            throw new IllegalArgumentException();
        }
        Post post = new Post(null, title, content, new Timestamp(System.currentTimeMillis()), null, new ArrayList<>(), author);
        post = postService.addPost(post);

        try {
            byte bytes[] = img.getBytes();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("/Users/bekkkdd/Desktop/IdeaProjects/Bitlab/Java EE/15.03.2021/blog_project/src/main/resources/imgs/" + "post-" + post.getId() + ".img")));
            bos.write(bytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/edit/{post_id}")
    public String editPost(
            @PathVariable(name = "post_id") Long postId,
            Model model
    ) {
        Post post = postService.getPostById(postId);
        model.addAttribute("post", post);

        List<User> authors = userServiceImpl.getAllUsers();
        model.addAttribute("authors", authors);

        return "editPost";
    }

    @PostMapping("/posts/edit")
    public String editPost(
            @RequestParam(name = "id") Long postId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(name = "new_author_id") Long newAuthorId
    ) {
        User newAuthor = userServiceImpl.getUserById(newAuthorId);
        if (newAuthor == null) {
            throw new IllegalArgumentException();
        }
        Post post = postService.getPostById(postId);
        post.setContent(content);
        post.setTitle(title);
        post.setAuthor(newAuthor);
        post.setEditDate(new Timestamp(System.currentTimeMillis()));
        postService.savePost(post);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{postId}")
    public String postById(
            Model model,
            @PathVariable Long postId
    ) {
        Post post = postService.getPostById(postId);
        model.addAttribute("post", post);
        return "postDetails";
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseBody
    public ResponseEntity<String> deletePostById(
            Model model,
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
