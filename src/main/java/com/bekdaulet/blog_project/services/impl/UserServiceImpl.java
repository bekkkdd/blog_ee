package com.bekdaulet.blog_project.services.impl;

import com.bekdaulet.blog_project.dtos.RegistrationUserDTO;
import com.bekdaulet.blog_project.exceptions.UserAlreadyExistException;
import com.bekdaulet.blog_project.models.Role;
import com.bekdaulet.blog_project.models.User;
import com.bekdaulet.blog_project.repositories.UserRepository;
import com.bekdaulet.blog_project.services.RoleService;
import com.bekdaulet.blog_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User secUser
                = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        User user = userRepository.getUserByUsername(secUser.getUsername());
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User registerNewUserAccount(RegistrationUserDTO userDto) throws UserAlreadyExistException {
        if (emailExist(userDto.getEmail()) && usernameExist(userDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role userRole = roleService.getRoleByName("USER");
        if (userRole == null) {
            roleService.addNewRole("USER");
        }
        user.setRoles(Arrays.asList(userRole));
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

    private boolean usernameExist(String username) {
        return userRepository.getUserByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User [" + s + "] not found!");
        }
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(user.getUsername() , user.getPassword(), mapRolesToAuthorities(user.getRoles()));

        return userDetails;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
