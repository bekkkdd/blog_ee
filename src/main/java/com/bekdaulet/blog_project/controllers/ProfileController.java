package com.bekdaulet.blog_project.controllers;

import com.bekdaulet.blog_project.models.Post;
import com.bekdaulet.blog_project.models.User;
import com.bekdaulet.blog_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("profile")
public class ProfileController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("{username}")
    public String profilePage(
            Model model,
            @PathVariable String username
    ){
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "profilePage";
    }

    @PostMapping("subscribe/{username}")
    public String subscribeProfile(
            Model model,
            @PathVariable String username
    ){
        User user = userService.getUserByUsername(username);
        User currentUser = userService.getCurrentUser();
        currentUser.getChannels().add(user);
        userService.saveUser(currentUser);
        model.addAttribute("user", user);
        return "redirect:/profile/" + username;
    }

    @PostMapping("unsubscribe/{username}")
    public String unsubscribeProfile(
            Model model,
            @PathVariable String username
    ){
        User user = userService.getUserByUsername(username);
        User currentUser = userService.getCurrentUser();
        currentUser.getChannels().remove(user);
        userService.saveUser(currentUser);
        model.addAttribute("user", user);
        return "redirect:/profile/" + username;
    }

    @GetMapping("edit")
    public String profileEditPage(
            Model model
    ){
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "profileEdit";
    }

    @PostMapping("edit")
    public String profileEditPage(
            @RequestParam String username,
            @RequestParam String password1,
            @RequestParam String password2
    ){
        User user = userService.getCurrentUser();
        if(username != null && username.length() > 4) {
            if(password1 != null && password1.length() > 6 &&
                    password2 != null && password2.length() > 6 &&
            password1.equals(password2)){
                user.setUsername(username);
                user.setPassword(encoder.encode(password1));
                userService.saveUser(user);
            }
        }

        return "redirect:/";
    }

    @GetMapping("posts")
    public String profilesPosts(
            Model model
    ){
        User user = userService.getCurrentUser();
        Collection<Post> postList = user.getPosts();
        model.addAttribute("posty", postList);
        return "posts";
    }
}
