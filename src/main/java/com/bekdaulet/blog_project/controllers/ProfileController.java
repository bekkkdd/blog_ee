package com.bekdaulet.blog_project.controllers;

import com.bekdaulet.blog_project.models.User;
import com.bekdaulet.blog_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("profile")
public class ProfileController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

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
}
