package com.bekdaulet.blog_project.repositories;

import com.bekdaulet.blog_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
    User getUserByUsername(String username);
}
