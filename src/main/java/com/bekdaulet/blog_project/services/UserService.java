package com.bekdaulet.blog_project.services;

import com.bekdaulet.blog_project.dtos.RegistrationUserDTO;
import com.bekdaulet.blog_project.exceptions.UserAlreadyExistException;
import com.bekdaulet.blog_project.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    public User registerNewUserAccount(RegistrationUserDTO userDto) throws UserAlreadyExistException;

    User getUserById(Long id);

    List<User> getAllUsers();

    User getCurrentUser();

    User saveUser(User u);
}
